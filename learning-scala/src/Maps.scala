import scala.collection.mutable.Map
import java.io.BufferedReader
import java.io.FileReader
import java.io.File
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.SortedMap
import scala.collection.mutable.LinkedHashMap
import java.util.Calendar
import classes.Car
import classes.BankAccount


object Maps {
  
  def discount(m: Map[String, Double], discount: Double): Map[String, Double] = {
    val discounted = Map[String, Double]()
    for((k,v) <- m) discounted(k) = v*(1.0-discount)
    discounted
  }
  
  def wordCountFromFile(fp: String): Map[String, Int] = {
    val wc = Map[String, Int]()
    val in = new BufferedReader(new FileReader(new File(fp)))
    var line: String = null
    while((line = in.readLine()) != Unit) {
      try {
        for(t<-line.split(" "))  wc.update(t, wc.getOrElse(t, 0)+1)    
      }
      catch {
        // check if string is empty failed
        // catching exception o.O
        case t: NullPointerException => return wc // TODO: handle error
      }
    }
    wc
  }
  
  def immutableWordCountFromFile(fp: String) : scala.collection.immutable.Map[String, Int] = {
    val map = wordCountFromFile(fp)
    val l = new ArrayBuffer[(String,Int)]
    for((k,v)<-map) l+=((k,v))
    l.toMap
  }

  
  def sortedWordCountFromFile(fp: String) : scala.collection.mutable.SortedMap[String, Int] = {
    val wc = SortedMap[String, Int]()
    val in = new BufferedReader(new FileReader(new File(fp)))
    var line: String = null
    while((line = in.readLine()) != Unit) {
      try {
        for(t<-line.split(" "))  wc.update(t, wc.getOrElse(t, 0)+1)    
      }
      catch {
        // check if string is empty failed
        // catching exception o.O
        case t: NullPointerException => return wc // TODO: handle error
      }
    }
    wc
  }
  
  def weekDaysMap(): scala.collection.mutable.Map[String, Int] = {
    val weekDays = LinkedHashMap("monday"->java.util.Calendar.MONDAY, "tuesday" -> Calendar.TUESDAY, "wednesday" -> Calendar.WEDNESDAY,
        "thursday" -> Calendar.THURSDAY, "friday" -> Calendar.FRIDAY)
    weekDays
  }
  
  def printJavaProperties(): Unit = {
    val props = scala.collection.JavaConversions.propertiesAsScalaMap(System.getProperties)
    var str = ""
    for((k,v)<-props)str+="%-25s%s %s\n".format(k, "|", v)
    println(str)
  }
  
  def minmax(a: Array[Int]): (Int,Int) = {
    (a.min,a.max)
  }
  
  def lteqgt(a: Array[Int], v: Int): (Int,Int,Int) = {
    var lt = 0
    var eq = 0
    var gt = 0
    for(e<-a) {
      if(e<v) lt+=1
      if(e==v)eq+=1
      if(e>v)gt+=1
      }
    (lt,eq,gt)
  }
  
  def main(args: Array[String]): Unit = {
    val map = sortedWordCountFromFile("wordcount_test")
    for((k,v)<-map)println(s"$k=$v")
    printJavaProperties
  
    println(minmax(Array(5,4,1,2,3)))
  
  
    println(lteqgt(Array(3,4,1,2,5,7,9), 4))
    
    println("HloWrl".zip("el old"))
  }
}