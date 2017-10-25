import java.io.File
import java.io.PrintWriter
import java.io.BufferedReader
import java.io.FileReader

object ControlStructures {

  def twice(op: Double => Double, x: Double): Double = op(op(x))

  def withPrintWriter(file: File)(op: PrintWriter => Unit): Unit = {
    val writer = new PrintWriter(file)
    try {
      op(writer)
    } finally {
      writer.close()
    }
  }

  def withBufferedReader(file: File)(op: BufferedReader => List[String]): List[String] = {
    val reader = new BufferedReader(new FileReader(file))
    try {
      op(reader)
    } finally {
      reader.close()
    }
  }

  def myAssert(predicate: => Boolean) = {
    if (!predicate) throw new AssertionError()
  }
  
  def main(args: Array[String]): Unit = {
    val v = twice(x => x + 2, 3)
    println(v)
    println(twice(Math.pow(_, 2), 10))

    val f = new File("/home/matija/Desktop/test")
    withPrintWriter(f) { printer =>
      printer.println("Testing magic scala code!")
    }

    val lines = withBufferedReader(f) { reader =>
      var line = ""
      var lines: List[String] = Nil
      do {
        line = reader.readLine()
        if (line != null) lines = line :: lines
      } while (line != null)
      lines
    }
    
    myAssert {5>3}

    println(lines)
  }

}