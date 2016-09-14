package com.hhf
/**
 * @description
 * use scala, spark to count words
 * debug 中间变量打印
 * @version V3.0
 * @author HHF
 * @url http://spark.apache.org/examples.html
 * @time 2016-5-18
 */

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.rdd.RDD

object WordCount3 {
    def main(args: Array[String]) {
        if(args.length != 2) {
            System.out.println("Usage: <src> <des>")
            System.exit(1)
        }
        val sparkConf = new SparkConf().setAppName("WordCount3")
        val sparkContext = new SparkContext(sparkConf)
        val lines = sparkContext.textFile(args(0))
        println("lines")
        lines.collect().foreach(println)
        val words = lines.flatMap(_.split("\t"))
        println("words")
        words.collect().foreach(println)
        val wordsMap = words.map(a => (a, 1))
        println("words map")
        wordsMap.collect().foreach(println)
        val counts = wordsMap.reduceByKey((a, b) => a+b)
        println("counts")
        counts.collect().foreach(println)
        counts.saveAsTextFile(args(1))
        sparkContext.stop()
    }
}