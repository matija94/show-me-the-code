import scala.util.Random


object Arrays {
  
  def makeArray(n:Int): Array[Int] = {
    val a = new Array[Int](n)
    for (i <- 0 until n) a(i) = Random.nextInt(n)  
    a
  }
  
  def swapAdjacent(a: Array[Int]): Unit = {
    for(i <- a.indices by 2) {
      val first = a(i)
      a(i) = a(i+1)
      a(i+1) = first
    }
  }
  
  def main(args: Array[String]): Unit = {
    val a = makeArray(10)
    println(a.mkString("[", ", ", "]"))
    
    swapAdjacent(a)
    println(a.mkString("[", ", ", "]"))
    
    
  }
  
}