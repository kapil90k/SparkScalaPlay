package Spark.Consumers

import org.apache.spark.sql.SparkSession

/* This class is reading data from defined partition of Kafka topic.
 * Putting data in seperate-2 topic is special case where we don't want to create multiple topics. In that case, we
 * create only one topic and segregate the data into multiple partition according to the business usecase.
 *
* */

object KafkaPartitionRead extends App
{
    val spark = SparkSession.builder.master("local").appName("Reading from Kafka Topics").getOrCreate()

  val df = spark.readStream
        .format("kafka")
        .option("kafka.bootstrap.servers","localhost:9092")
        .option("assign","{\"voice-topic\":[1]}")
        .option("startingOffsets","earliest")
        .load()

  println("Printing Schema====>>")
  df.printSchema()

  val df2 = df.selectExpr("CAST(key AS STRING)","CAST(value AS STRING)","to_json(struct(*)) AS value","CAST(topic AS STRING)","CAST(partition AS Integer)")
  val query = df2.writeStream.outputMode("append").format("console").start()
  query.awaitTermination()

  spark.stop()
}
