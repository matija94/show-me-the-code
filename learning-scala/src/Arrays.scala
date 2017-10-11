import scala.util.Random
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Buffer


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
  
  private def helper(n: Int): TraversableOnce[Int] = {
    val indicies = new ArrayBuffer[Int]()
    for (i <- 0 until n) if(i%2==0) indicies+=i+1 else indicies+=i-1
    indicies
  }
  
  def swapAdjacent2(a: Array[Int]): TraversableOnce[Int] = {
    for(i<- helper(a.length)) yield a(i)
  }
 
  def positivesFirst(a: Array[Int]): TraversableOnce[Int] = {
    val ans = new ArrayBuffer[Int]()
    val neg = new ArrayBuffer[Int]()
    for(e <- a) if(e>0) ans+=e
    for(e <- a) if(e<=0) neg+=e
    ans++=neg
  }
  
  def avg(a: Array[Double]): Double = {
    a.sum/a.length
  }
  
  def reverseSorted(a : Any): Traversable[Int]= {
    // looks bad
    if (a.isInstanceOf[ArrayBuffer[Int]]) a.asInstanceOf[ArrayBuffer[Int]].sortWith(_ > _)
    else if (a.isInstanceOf[Array[Int]]) a.asInstanceOf[Array[Int]].sortWith(_ > _)
    else throw new IllegalArgumentException()
  }
  
  def distinct(a: ArrayBuffer[Int]): ArrayBuffer[Int] = {
    a.distinct
  }
  
  def main(args: Array[String]): Unit = {
    val a = makeArray(10)
    println(a.mkString("[", ", ", "]"))
    
    swapAdjacent(a)
    println(a.mkString("[", ", ", "]"))
    
    val b = swapAdjacent2(a)
    println(b.mkString("[", ", ", "]"))
    
    val ab = new ArrayBuffer[Int]()
    ab += (1,2,3,4,4)
    val b_reverseSort = reverseSorted(ab.toArray)
    
    println(b_reverseSort.mkString("[", ", ", "]"))
    
    val pos_neg = Array(-1,1,3,-2,-1)
    println(positivesFirst(pos_neg).mkString("[", ", ", "]"))
    println(distinct(ab).mkString("[", ", ", "]"))
    
    val scores = Array(2.4,3.2,1.5)
    println(avg(scores))
  
    val america = "America/"
    val americaTimeZones: Array[String] = java.util.TimeZone.getAvailableIDs filter { tz => tz.startsWith(america) } map {tz => tz.drop(america.length)} sorted;
    println(americaTimeZones.mkString(", "))
  
  
  
    import java.awt.datatransfer._
    
    /*val flavors = SystemFlavorMap.getDefaultFlavorMap().asInstanceOf[SystemFlavorMap]
    val nativeFlavors: Buffer[DataFlavor] = flavors.getFlavorsForNative(DataFlavor.imageFlavor.toString)*/
  
  }
  
}