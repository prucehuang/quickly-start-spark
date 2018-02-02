package com.git.huanghaifeng.spark.streaming
/**
  * @description
  * 监听localhost的7777端口，处理每一行的输入
  * nc -lk 7777 开启端口 发送数据
  * nc64.exe -lp 7777 开启端口 发送数据
  * 运行代码
  * spark-submit --class com.hhf.spark.streaming.StreamingSocket ./sparkStreamingExample.jar 192.168.9.223 7777
  * @version V1.0
  * @author HHF
  * @time 2016-5-24
  */

import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.SparkConf
import org.apache.spark.streaming.Seconds
import org.apache.spark.streaming.StreamingContext

object StreamingSocket {
    def main(args: Array[String]) {
        if (args.length < 2) {
            System.err.println("Usage: NetworkWordCount <hostname> <port>")
            System.exit(1)
        }

        val conf = new SparkConf()
        conf.setAppName("StreamingSocket")
        conf.setMaster("local")
        // Create a StreamingContext with a 1 second batch size
        val ssc = new StreamingContext(conf, Seconds(10))
        // Create a DStream from all the input on port 7777
        val lines = ssc.socketTextStream(args(0), args(1).toInt)
        lines.print()
//        val errorLines = processLines(lines)
//        // Print out the lines with errors, which causes this DStream to be evaluated
//        errorLines.print()
        // start our streaming context and wait for it to "finish"
        ssc.start()
        // Wait for 10 seconds then exit. To run forever call without a timeout
        ssc.awaitTermination()
        ssc.stop()
    }

    def processLines(lines: DStream[String]) = {
        // Filter our DStream for lines with "error"
        lines.filter(_.contains("error"))
    }
}