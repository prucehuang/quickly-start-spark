package com.git.huanghaifeng.spark.helloworld

object HelloWorld {
    def main(args: Array[String]) {
        println("haha, hello, I'am coming")
        println(Array[String]("a", "b", "", "c").mkString("###").split("###").apply(2))
        println("---end---")
    }
}