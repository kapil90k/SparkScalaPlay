/*
package dao

import slick.driver.MySQLDriver.api._

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

class ConnectionDao(db:Database) {

  val connections = TableQuery[ConnectionTable]

  def searchByEmpId1(empId:String): Future[Int] =
  {
    db.run(connections.filter(_.empId===empId).length.result)
  }

  def searchByEmpId2(empId:String): String =
  {
    val query =new StringBuilder
    query.append(s"""select empName from students where empId = $empId""")
    val sql=query.toString()
    val runSql=db.run(sql"""#$sql""".as[String])
    Await.result(runSql,50 seconds).head
  }

  case class Employee(empId:String,empName:String,empCourse:String)

  class ConnectionTable(tag:Tag) extends Table[Employee](tag,None,"students")
  {
    def empId=column[String]("empId")
    def empName=column[String]("empName")
    def empCourse=column[String]("empCourse")

    def * = (empId,empName,empCourse) <> (Employee.tupled,Employee.unapply)
  }

}
*/
