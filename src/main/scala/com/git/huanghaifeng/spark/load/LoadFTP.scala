/**
  * Illustrates loading a text file from FTP
  */
package com.git.huanghaifeng.spark.load

import org.apache.spark._
import org.apache.spark.SparkContext._

object LoadFTP {
    def main(args: Array[String]) {
        val conf = new SparkConf
        conf.setMaster("local").setAppName("LoadFTP")
        val sc = new SparkContext(conf)
        val file = sc.textFile("ftp://anonymous:pandamagic@ftp.ubuntu.com/ubuntu/ls-LR.gz")
        println(file.collect().mkString("\n"))
    }
}
