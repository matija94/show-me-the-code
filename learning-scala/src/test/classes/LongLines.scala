package test.classes

import scala.io.Source
import java.io.File

object LongLines {
  
  def processFile(name: String, width: Int): Unit = {

    def processLine(line: String): Unit = {
       if (line.length > width) println(s"$name: $width $line") 
    }
    
    val source = Source.fromFile(new File(name))
    for(line<-source.getLines())
      processLine(line)
  }
  
  def main(args: Array[String]): Unit = {
    processFile("/home/matija/Desktop/test", 10)
  }
}