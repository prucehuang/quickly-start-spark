package com.git.huanghaifeng.spark.basic

import org.apache.spark.SparkContext

object BasicCombineByKey {

    def main(args: Array[String]) {
        val master = args.length match {
            case x: Int if x > 0 => args(0)
            case _               => "local"
        }

        val sc = new SparkContext(master, "PerKeyAvg", System.getenv("SPARK_HOME"))
        val input = sc.parallelize(List(("coffee", 1), ("coffee", 2), ("panda", 4)))
        val result = input.combineByKey(
            (v) => (v, 1),
            (acc: (Int, Int), v) => (acc._1 + v, acc._2 + 1),
            (acc1: (Int, Int), acc2: (Int, Int)) => (acc1._1 + acc2._1, acc1._2 + acc2._2)
        )
        
        val result_map = result.map{ case (key, value) => (key, value._1 / value._2.toFloat) }
        result_map.collectAsMap().map(println(_))
        
        val result_map_value = result.mapValues(record => (record._1 / record._2.toFloat))
        result_map_value.collectAsMap().map(println(_))
    }
}