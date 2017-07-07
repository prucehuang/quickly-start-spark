/**
  * Loads a simple sequence file of people and how many pandas they have seen.
  */
package com.git.huanghaifeng.spark.load

import org.apache.spark._
import org.apache.spark.SparkContext._
import org.apache.hadoop.io.{ IntWritable, Text }

object LoadFileSequence {
    def main(args: Array[String]) {
        val master = args(0)
        val file_path = args(1)

        val sc = new SparkContext(master, "BasicSequenceFile", System.getenv("SPARK_HOME"))
        val out_data = sc.parallelize(List(("Holden", 3), ("Kay", 6), ("Snail", 2)))
        out_data.saveAsSequenceFile(file_path)

        val in_data = sc.sequenceFile(file_path, classOf[Text], classOf[IntWritable]).map{
            case (x, y) =>
                (x.toString, y.get())
        }
        println(in_data.collect().toList)
    }
}
