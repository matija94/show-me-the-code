package test.classes

class CheckSumAccumulator {
  private var sum = 0
  def add(b: Byte): Unit = sum += b
  def checksum(): Int = ~(sum & 0xFF) + 1
}

object CheckSumAccumulator {
  
  val cache = collection.mutable.Map.empty[String,Int]

  def apply(): CheckSumAccumulator = new CheckSumAccumulator()

  def calculate(s: String): Int = {
    if (cache contains s) cache(s)
    else {
      val acc = CheckSumAccumulator()
      s.foreach {c=> acc.add(c.toByte)}
      val cs = acc.checksum
      cache += (s->cs)
      cs
    }
  }
  
  def main(args: Array[String]): Unit = {
		  println(2&0xFF)   
		  println(16 >> 2 ) // right shifting is same as division by powers of two
		  println( 4 << 2) // left shifting is same as multiplication by powers of two
		  println( 26 << 1) // 26 * 2
		  println('M'.toByte)
		  println(CheckSumAccumulator.calculate("Matija"))	  
		  if (args.length > 0) args.foreach {arg=> println(arg + ": " + CheckSumAccumulator.calculate(arg))} 
		  
  }
}

