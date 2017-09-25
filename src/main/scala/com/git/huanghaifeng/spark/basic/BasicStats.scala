package com.git.huanghaifeng.spark.basic

import org.apache.spark.SparkContext

object BasicStats {
    def main(args: Array[String]) {
        val master = args.length match {
            case x: Int if x > 0 => args(0)
            case _               => "local"
        }
        val sc = new SparkContext(master, "BasicStats", System.getenv("SPARK_HOME"))

        val input = sc.parallelize(Seq(1, 2, 3, 4, 5)).map(_.toDouble)
        val input_stats = input.stats()
        println(input_stats.variance)
    }
}