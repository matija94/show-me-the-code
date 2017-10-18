package scripts.simple

import scala.io.Source

class LineLen(args: Array[String]){
 
  def widthOfLineLength(line: String): Int = line.length.toString.length
  
  if (args.length == 0) Console.err.println("Please enter file name")
  else {
    val lines = (Source fromFile args(0) getLines) toList
    val longestLine = lines reduceLeft {(l1,l2)=> if (l1.length > l2.length) l1 else l2}
    
    val maxWidth = widthOfLineLength(longestLine)
    
    lines.foreach {
      line => 
      val numSpaces = maxWidth - widthOfLineLength(line)
      val padding = " " * numSpaces
      println(padding + line.length + " | " + line)
    }
  }

}


object LineLen {
  
  def apply(args: Array[String]) = new LineLen(args)
  
  def main(args: Array[String]): Unit = {
    val custom_args = if (args isEmpty) Array("wordcount_test") else args
    LineLen(custom_args)
  }
}