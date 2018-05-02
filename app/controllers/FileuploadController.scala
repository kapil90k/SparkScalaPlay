package controllers

import java.io.File
import java.util.Date
import scalikejdbc._
import com.univocity.parsers.csv.{CsvParser, CsvParserSettings}
import javax.inject.Inject
import play.api.Configuration
import play.api.mvc.{Action, Controller}

class FileuploadController @Inject()(config: Configuration) extends Controller {

  def importRules() = Action { implicit request => {
    println("************ Atleast came inside importRules************")
    println("I am changing some text here to commit on github")
    val fileParts = request.body.asMultipartFormData.get.file("myFile")

    val file=fileParts.map(filePart=>{
      val fileName=filePart.filename
      val movedFileName=System.getProperty("java.io.tmpdir")+"play\\"+new Date().getTime.toString+"\\"+fileName
      val aa=new File(movedFileName).getParentFile.mkdirs()
      filePart.ref.moveTo(new File(movedFileName)).getAbsoluteFile
    })

    file match
      {
      case None =>
        Ok("File Not Found")
      case Some(file) =>
        insertCsvToDb(file)
        Ok("File saved to database, please query table")
    }
  }
  }

  import scala.collection.JavaConversions._
  private def insertCsvToDb(file: File): Unit =
  {
    val setting=new CsvParserSettings()
    setting.setLineSeparatorDetectionEnabled(true)
    setting.setDelimiterDetectionEnabled(true)
    setting.setQuoteDetectionEnabled(true)
    setting.setHeaderExtractionEnabled(true)
    val reader = new CsvParser(setting)

    val rows = reader.parseAll(file).filter(x=>x.size==5)
  println("**********Printing file***************")
    for(i<- 0 until rows.size)
    {
      val row=rows(i)
      for(i<-0 until row.size)
        print(row(i)+" ")
      println
    }
    println("**********Printed file***************")


    DB localTx{
      implicit  session =>{
        rows.map(row=>{
          val testRow = TestCaseClass(row(0),row(1),row(2),row(3).toInt,row(4))
          TestDao.insert(testRow)
        })
      }
    }


  }
}

case class TestCaseClass(fName:String,lName:String,city:String,age:Int,married:String)
object TestDao extends SQLSyntaxSupport[TestCaseClass]
{
    def insert(testRow: TestCaseClass)(implicit session: DBSession = autoSession):Int={
      val id = sql"""insert into family(fName,lName,city,age,married) values(${testRow.fName},${testRow.lName},${testRow.city},${testRow.age},${testRow.married})""".
        updateAndReturnGeneratedKey.apply()
      id.toInt
    }
}