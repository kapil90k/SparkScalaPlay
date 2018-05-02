package Spark.Consumers

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{StringType, StructField, StructType}

object CSVReader extends App
{
    val spark = SparkSession.builder.master("local").appName("CSV File Consumer").getOrCreate()
    val sc = spark.sparkContext
  val line = "My name is Kapil Kumar and I am not a terrorist"
    val listRdd = sc.parallelize(line.split(" ").toList)
    val rddStruct=new StructType(Array(
      StructField("Name",StringType,true),
      StructField("Age",StringType,true),
      StructField("City",StringType,true),
      StructField("Country",StringType,true)
    ))

  val rawData = spark.readStream.schema(rddStruct).csv("Some folder path")
  println("Printing Schema===>>")
  rawData.printSchema()

  val query = rawData.selectExpr("CAST(Name AS STRING) as key","to_json(struct(*)) AS value")
              .writeStream
              .format("kafka")
              .option("kafka.bootstrap.servers","localhost:9092")
              .option("topic","test")
              .option("checkpointLocation","Some Filesystem Location")
              .start()

  query.awaitTermination()

  spark.stop()
}
