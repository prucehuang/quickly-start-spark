package com.git.huanghaifeng.spark.basic

import scala.collection.JavaConverters._
import org.apache.spark.util.Utils

object BasicSystemProperties {
    def main(args: Array[String]): Unit = {
        println("Environment variables containing SPARK_TEST:")
        val env = System.getenv()
        env.asScala.foreach(println)
        //.filter{
        //    case (k, _) => k.contains("SPARK_TEST")
        //}.foreach(println)

//        println("System properties containing spark.test:")
//        val properties = Utils.getSystemProperties
//        properties.foreach(println)
        //filter{
        //    case (k, _) => k.toString.contains("spark.test")
        //}.foreach(println)

        val numSecondsToSleep = 10
        for (i <- 1 until numSecondsToSleep) {
            println(s"Alive for $i out of $numSecondsToSleep seconds")
            Thread.sleep(100)
        }
    }
}