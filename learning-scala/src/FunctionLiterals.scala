

object FunctionLiterals {
  
  def sum(a: Int, b: Int, c: Int): Int = {
    a+b+c
  }
  
  // function value - partial function
  var partiallySum = sum _
  // function value - partial function with 2 default arguments
  var partiallySum1 = sum(1, _: Int, 2)
  
  // function literal
  var shiftUp = (x: Int) => x+1
  
  var shiftDown = (_: Int) -1
  
  def moreIncrease(more: Int) = (_: Int) + more
  
  // closure
  var increase = moreIncrease(10)

  // tail recursion functions are detected by scala and are compiled as while implementation(therefore speed and memory usage stays the same and looks are all fancy recursion)
  def countdown(int: Integer): Unit = {
    if(int == 0) throw new Exception()
    else countdown(int - 1)
  }
  
  
  def main(args: Array[String]): Unit = {
     val up = shiftUp(1)
     val down = shiftDown(1)
  
     println(up)
     println(down)
     
     val sumVal = sum(1,2,3)
     println(sumVal)
     println(partiallySum(1,5,4))
     println(partiallySum1(5))
  
  
     println(increase(25))
  
     countdown(5)
  }
  
}