package com.hhf
/**
 * @description
 * use scala, spark to count words
 * @version V1.0
 * @author HHF
 * @url http://spark.apache.org/examples.html
 * @time 2016-5-18
 */

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._

object WordCount {
    def main(args: Array[String]) {
        val conf = new SparkConf()
        val sc = new SparkContext(conf)
 
        val textFile = sc.textFile("hdfs:/test/huanghaifeng/wordcount/input")
        val counts = textFile.flatMap(line => line.split(" "))
                             .map(word => (word, 1))
                             .reduceByKey(_ + _)
        counts.saveAsTextFile("hdfs:/test/huanghaifeng/wordcount/output")
        sc.stop()
    }
}