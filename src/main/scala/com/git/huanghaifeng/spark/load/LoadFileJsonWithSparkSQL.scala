/**
  * Illustrates loading JSON data using Spark SQL
  */
package com.git.huanghaifeng.spark.load

import org.apache.spark._
import org.apache.spark.sql.SQLContext

object LoadFileJsonWithSparkSQL {
    def main(args: Array[String]) {
        if (args.length != 2) {
            println("Usage: [sparkmaster] [inputFile]")
            exit(1)
        }
        val master = args(0)
        val inputFile = args(1)
        val sc = new SparkContext(master, "LoadJsonWithSparkSQL", System.getenv("SPARK_HOME"))
        val sqlCtx = new SQLContext(sc)
        val input = sqlCtx.jsonFile(inputFile)
        input.printSchema()
        input.collect().foreach { println }
    }
}
