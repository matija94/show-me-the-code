package test.classes

object MultiplicationTable {
  
  
  def multiTable = {
    val table = for(row <- 1 to 10) yield makeRow(row).mkString
    table.mkString("\n")
  }

  def makeRow(row: Int) = {
    for(col <- 1 to 10) yield {
      val prod = (col*row).toString
      val padding = " " * (4 - prod.length)
      if(col>1) padding + prod else if (row>=10) prod else " " + prod
    }
  }
  
  def main(args: Array[String]): Unit = {
    println(multiTable)
  }
}