package Spark.Producers

import java.io.{File, FileNotFoundException}
import java.nio.file._
import java.util.{Properties, Scanner}

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

/*This class is used to create Kafka producer which will be monitoring one directory for any input file
* and immediately put it into defined Kafka topic*/

object FolderMonitor
{
  def main(args: Array[String]): Unit = {
    val faxFolder = Paths.get("D:\\monitoring")
    val kafkaProperties = new Properties()
    kafkaProperties.put("bootstrap.servers", "localhost:9092")
    kafkaProperties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    kafkaProperties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    var producer: KafkaProducer[String, String] = new KafkaProducer(kafkaProperties)
    val watchService:WatchService = FileSystems.getDefault.newWatchService
    faxFolder.register(watchService, StandardWatchEventKinds.ENTRY_CREATE)

    var valid = true
    do {
      val watchKey: WatchKey = watchService.take
      import scala.collection.JavaConversions._
      for (event <- watchKey.pollEvents()) {
        if (StandardWatchEventKinds.ENTRY_CREATE == event.kind) {
          val fileName = event.context.toString
          println("File Created:" + fileName + " ***Printing contents now")
          val file = new File("D:\\monitoring\\" + fileName)
          var sc:Scanner = null
          try {
            Thread.sleep(1000)
            sc = new Scanner(file)
            while(sc.hasNextLine){
              val line:String=sc.nextLine()
              val value=line.replace(" ","_")+line.split(" ").length
              producer.send(new ProducerRecord("test", value))
            }
          } catch {
            case fnf: FileNotFoundException =>
              System.out.println(fnf.getMessage + " ***** ")
          } finally {
            println("Closing scanner object")
            sc.close
          }
        }
      }
      valid = watchKey.reset
    } while (valid)
  }
}
