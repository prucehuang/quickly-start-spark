package com.git.huanghaifeng.spark.basic

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object BasicFilterUnion {
    def main(args: Array[String]) {
        val sc = new SparkContext(args(0), "BasicFilterUnion", System.getenv("SPARK_HOME"))
        val inputRDD = sc.textFile(args(1))
        val errorsRDD = inputRDD.filter(_.contains("error"))
        val warningsRDD = inputRDD.filter(_.contains("warn"))
        val badLinesRDD = errorsRDD.union(warningsRDD)
        println(badLinesRDD.collect().mkString("\n"))
    }
}
