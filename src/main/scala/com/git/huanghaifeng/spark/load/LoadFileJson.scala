/**
  * Illustrates a simple map partition to parse JSON data in Scala
  * Loads the data into a case class with the name and a boolean flag
  * if the person loves pandas.
  */
package com.git.huanghaifeng.spark.load

import org.apache.spark._
import com.alibaba.fastjson.JSON

object LoadFileJson {
    case class Person(name: String, lovesPandas: Boolean)

    def main(args: Array[String]) {
        if (args.length < 3) {
            println("Usage: [sparkmaster] [inputfile] [outputfile]")
            exit(1)
        }
        val master = args(0)
        val inputFile = args(1)
        val outputFile = args(2)
        val sc = new SparkContext(master, "BasicParseJson", System.getenv("SPARK_HOME"))
        val input = sc.textFile(inputFile)
        //input.flatMap(msg => if (JSON.parseObject(msg).getString("name").contentEquals("Sparky The Bear")) { msg } else { "" }).collect().foreach(print)

        input.map(JSON.parseObject(_)).saveAsTextFile(outputFile)
    }
}
