package com.git.huanghaifeng.spark.sql.hive

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.sql.hive.HiveContext

case class HappyPerson(handle: String, favouriteBeverage: String)
case class Person(id: Int, name: String, age: Int)

object SQLHiveContext {

    def main(args: Array[String]) {
        if (args.length < 1) {
            println("Usage inputFile [spark.sql.inMemoryColumnarStorage.batchSize]")
            return
        }
        val inputFile = args(0)
        val batchSize = if (args.length == 3) {
            args(2)
        } else {
            "200"
        }

        val conf = new SparkConf()
        conf.set("spark.sql.codegen", "false")
        conf.set("spark.sql.inMemoryColumnarStorage.batchSize", batchSize)
        conf.setAppName("SQL HIVE Context")
        conf.setMaster("local")
        val sc = new SparkContext(conf)
        val hiveCtx = new HiveContext(sc)
        import hiveCtx.implicits._
        
        // Load some tweets
        val input = hiveCtx.jsonFile(inputFile)
        // Print the schema
        input.printSchema()
        // Register the input schema RDD
        input.registerTempTable("tweets")
        hiveCtx.cacheTable("tweets")

        // Select tweets based on the retweetCount
        val topTweets = hiveCtx.sql("SELECT text, retweetCount FROM tweets ORDER BY retweetCount LIMIT 10")
        topTweets.collect().map(println(_))

        val topTweetText = topTweets.map(row => row.getString(0))
        topTweets.collect().map(println(_))

        // Create a person and turn it into a Schema RDD
        val happyPeopleRDD = sc.parallelize(List(HappyPerson("holden", "coffee"))).toDF()
        happyPeopleRDD.registerTempTable("happy_people")

        // UDF
        hiveCtx.udf.register("strLenScala", (_: String).length)
        val tweetLength = hiveCtx.sql("SELECT strLenScala('tweet') FROM tweets LIMIT 10")
        tweetLength.collect().map(println(_))

        // Two sums at once (crazy town!)
        val twoSums = hiveCtx.sql("SELECT SUM(user.favouritesCount), SUM(retweetCount), user.id FROM tweets GROUP BY user.id LIMIT 10")
        twoSums.collect().map(println(_))

        sc.stop()
    }
}