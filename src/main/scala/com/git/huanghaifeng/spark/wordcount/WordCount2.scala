package com.hhf
/**
 * @description
 * use scala, spark to count words
 * 增加了reduce后的方法,将结果输出到控制台 1>result
 * @version V2.0
 * @author HHF
 * @url http://spark.apache.org/examples.html
 * @time 2016-5-18
 */

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._

object WordCount2 {
    def main(args: Array[String]) {
        val conf = new SparkConf()
        val sc = new SparkContext(conf)
                             
        if (args.length < 1) {
          System.err.println("Usage: <inputFiles>")
          System.exit(1)
        }
        val text = sc.textFile(args(0))
        val counts = text.flatMap(_.split(" "))
            .map((_, 1))
            .reduceByKey(_+_)
            .collect()
            .foreach(println)
        sc.stop()
    }
}

