package com.git.huanghaifeng.spark.worldcount
/**
 * @description
 * use scala, spark to count words
 * @version V1.0
 * @author HHF
 * @url http://spark.apache.org/examples.html
 * @time 2016-5-18
 */

import org.apache.spark.{SparkConf, SparkContext}

object WordCount {
    def main(args: Array[String]) {
        val conf = new SparkConf()
        val sc = new SparkContext(conf)

        val textFile = sc.textFile("/hhf/LICENSE")
        val counts = textFile.flatMap(line => line.split(" "))
                             .map(word => (word, 1))
                             .reduceByKey(_ + _, 18).repartition(10)
        counts.saveAsTextFile("/hhf/LICENSE.result")
        sc.stop()
    }
}