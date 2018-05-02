package controllers

import java.util.Properties
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import play.api.mvc.{Action, Controller}
import utilities.DataGenerator

class KafkaController extends Controller
{
  //This is a Kafka Producer. It will produce cdr record for VOICE/SMS and put it in seperate kafka topic
    def cdrProducer(num:Integer) = Action{
      val dg=new DataGenerator()
      println("Inside cdrProducer method===>>>>")
      println(s"Value of num: $num")
      val cdrData=dg.generateCDR(num)
      val kafkaProperties = new Properties()
      kafkaProperties.put("bootstrap.servers","localhost:9092")
      kafkaProperties.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer")
      kafkaProperties.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer")
      var producer: KafkaProducer[String, String] = new KafkaProducer(kafkaProperties)

      var tCount = 0
      var vCount = 0
      var sCount = 0
      println("Printing cdrData")
      cdrData.foreach(println)
      cdrData.foreach(line=>{
        tCount=tCount+1
        val splits=line.split(",")
        if(splits(5).equals("VOICE")){
          vCount+=vCount+1
          producer.send(new ProducerRecord("voice-topic","VOICE",line))
        }
        if(splits(5).equals("SMS")){
          producer.send(new ProducerRecord("text-topic","SMS",line))
          sCount=sCount+1
        }
      })
      val result:String=s"Data Sent to Kafka Topic ${cdrData.length} $vCount $sCount $tCount ${cdrData(0).split(",")(5)} \n\n"
      Ok(result+cdrData.mkString("\n"))
    }
}
