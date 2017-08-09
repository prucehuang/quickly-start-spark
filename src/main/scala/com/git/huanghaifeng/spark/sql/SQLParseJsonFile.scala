package com.git.huanghaifeng.spark.sql

import org.apache.spark.SparkContext
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.hive.HiveContext


object SQLParseJsonFile {
    def main(args: Array[String]) {
        val master = args.length match {
            case x: Int if x > 0 => args(0)
            case _               => "local"
        }
        val sc = new SparkContext(master, "SQLParseJsonFile", System.getenv("SPARK_HOME"))
        val sqlContext = new SQLContext(sc)
        val df = sqlContext.read.json("file:///tmp/json.txt")
        df.show()

        df.printSchema()

        df.select("uid").show()

        df.select("aid", "uid").show()
        df.select(df("aid"), df("uid") + 1).show()

        df.filter(df("event_time") > 21).show()

        df.groupBy("event_time").count().show()
    }
}