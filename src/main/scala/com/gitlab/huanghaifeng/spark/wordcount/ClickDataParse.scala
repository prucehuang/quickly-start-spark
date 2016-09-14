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
import scala.util.parsing.json._

object ClickDataParse {
    def main(args: Array[String]) {
        if(args.length != 2) {
            System.out.println("Usage: <src> <des>")
            System.exit(1)
        }
        val sparkConf = new SparkConf().setAppName("click_data_parse")
        val sparkContext = new SparkContext(sparkConf)
        val lines = sparkContext.textFile("hdfs:/test/huanghaifeng/click-reformat/input/")
        println("lines")
        lines.collect().foreach(lineMap)
//        val words = lines.flatMap(line => lineMap(line))
//        println("words")
//        words.collect().foreach(println)
//        val wordsMap = lines.map(a => (a, 1))
//        println("words map")
//        wordsMap.collect().foreach(println)
//        val counts = wordsMap.reduceByKey((a, b) => a+b)
//        println("counts")
//        counts.collect().foreach(println)
//        counts.saveAsTextFile("hdfs:/test/huanghaifeng/click-reformat/output/")
        sparkContext.stop()

        def lineMap(line:String) = {
//            val line = "{\"app\":\"coolpad\",\"app_ver\":\"\",\"article_click_extra\":{\"content_id\":\"1153203689536502006\",\"content_type\":\"8\",\"cpack\":{\"abtest_ver\":\"-1\"}},\"event_time\":\"2016-05-21 12:59:58\",\"model\":\"Coolpad+7275\",\"network\":\"\",\"scenario\":{\"level1\":\"0x01\",\"level1_type\":\"0\",\"level2\":\"0x00\"},\"uid\":\"+8617603370487\"}"
            val lineJson = JSON.parseFull(line)
            var app = ""
      		  var uid = "" 
      		  var abtestVer = ""
      		  var scenario = ""
      		  type StringMap = Map[String,Any]
            lineJson match {  
                // Matches if jsonStr is valid JSON and represents a Map of Strings to Any  
                
                case Some(map: StringMap) => {
                    app = map("app").toString()
              		  uid = map("uid").toString()
              		  abtestVer = map("article_click_extra") match {  
                        case map1:StringMap => {
                            map1("cpack") match {  
                                case map2: StringMap => {map2("abtest_ver").toString()}
                            }
                        }
                        case None => "Parsing failed"  
                        case other => "Unknown data structure: " + other
                    }
               		  scenario = map("scenario") match {  
                        case map1:StringMap => {
                            map1("level1_type") +"_"+ map1("level1") +"_"+ map1("level2")
                        }
                        case None => "Parsing failed"  
                        case other => "Unknown data structure: " + other
                    }
                    println("foreach one line")                		
                    println(app)
                    println(uid)
                    println(abtestVer)
                    println(scenario)
                }
                case None => println("Parsing failed")  
                case other => println("Unknown data structure: " + other)  
            }  
            (app +"#"+ abtestVer +"#"+ scenario, 1)
        }     
    }
}