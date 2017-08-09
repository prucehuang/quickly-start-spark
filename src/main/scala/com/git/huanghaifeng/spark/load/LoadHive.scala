/**
  * Illustrates loading Hive data using Spark SQL
  */
package com.git.huanghaifeng.spark.load

import org.apache.spark._
import org.apache.spark.sql.hive.HiveContext

object LoadHive {
    def main(args: Array[String]) {
        if (args.length < 1) {
            println("Usage: [sparkmaster]")
            return
        }
        val master = args(0)
        val sc = new SparkContext(master, "LoadHive", System.getenv("SPARK_HOME"))
        val hiveCtx = new HiveContext(sc)
        //val input = hiveCtx.sql("FROM src SELECT key, value")
        hiveCtx.sql("show tables").show()
    }
}
