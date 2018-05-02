package Spark.Consumers

import org.apache.spark.sql.SparkSession

object KafkaTopicRead extends App
{
    val sparkJob = new SparkJob()
  sparkJob.runJob()
  sparkJob.stopJob()
}

class SparkJob() extends Serializable
{
  val sparkSession = SparkSession.builder().master("local[*]").appName("Spark reading Kafka Topic").getOrCreate()
  def runJob() = {
    import sparkSession.implicits._
    val cols = List("user_id","time","event")
    val lines = sparkSession.readStream
      .format("kafka")
      .option("subscribe","test")
      .option("kafka.bootstrap.servers","localhost:9092")
      .option("startingOffsets","latest")
      .load()
      .selectExpr("CAST(value as STRING)","CAST(topic as STRING)","CAST(partition as INTEGER)")
      .as[(String,String,Integer)]

    val df = lines.map(line=>{
    val clms = line._1.split(" ")
      (clms(0),clms(1),clms(2))
    }).toDF(cols : _*)

    println("****************** Printing Schema ****************************")
    df.printSchema()

    df.writeStream
      .format("console")
      .outputMode("append")
      .start()
      .awaitTermination()
  }
  def stopJob() = {
    sparkSession.stop()
  }

}
