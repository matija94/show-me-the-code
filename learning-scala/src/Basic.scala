import java.time.LocalDate
import java.util.Date




object Test {
  
  implicit class DateInterpolator(val sc: StringContext) extends AnyVal {
    def date(args: Any*): LocalDate = {
      if(args.length != 3) throw new IllegalArgumentException("not valid argument")
      for(part <- sc.parts if part.length>0) if (part != "-") throw new IllegalArgumentException("not valid arg")
      val year: Int = args(0).toString.toInt; val month: Int = args(1).toString.toInt; val day: Int = args(2).toString.toInt
      LocalDate.of(year, month, day)
    }
  }
    // scala has functions which is java static method equivalent
  // return type is ommited since we do not need it as this function is non-recursive
  def abs(x: Double) = if(x>=0) x else -x
  
  // recursive functions do need return type
  def fact(x: Int):Int = {
    if (x >0) x*fact(x-1)
    else 1
  }
  
  // function can have default argument as in python
  def decorate(str: String, left: String = "[", right: String = "]") = left + str + right
  
  // passing multiple arguments same as in python
  def sum(ints: Int*) = {
    var ans = 0
    for(arg <- ints) ans+=arg
    ans
  }
  
  // recursive sum
  def recursiveSum(ints: Int*): Int = {
    if (ints.length == 0) 0
    else ints.head + recursiveSum(ints.tail : _*) // ints.tail is a sequence and our function excepts collection of arguments and not just one, there fore with : _* we tell the complier to consider sequence as collection of args
  }

  // void function for side-effects. NO EQUAL SIGN
  def box(s: String) {
    val border = "-" * (s.length + 2)
    print(f"$border%n|$s|%n$border%n")
  }
  
  // or we can define method signature as this, this is as well void function
  def boxExplicitUnit(s: String): Unit =  {
    val border = "-" * (s.length + 2)
    print(f"$border%n|$s|%n$border%n")
  }
  
  // lazy values will never be evaluated until they are accessed
  lazy val words = scala.io.Source.fromFile("bla").mkString
  
  // returns -1 if negative 0 if zero 1 if positive
  def signum(int: Int) = {
    if (int==0) 0
    else if (int >0) 1 else -1
  }
  
  def countdown(int: Int) {
    for (i <- 0 to int) print(f"$i%n")
  }
  
  def product(str: String) = {
    str.foldLeft(1L)(_ * _.toInt)
  }
  
   def recursiveProduct(str: String, start: Int = 0): Long = {
    if(start == str.length) 1L else str(start).toInt * recursiveProduct(str, start+1)
  }
   
   def power(base: Int, exponent: Int): Double = {
     if (exponent < 0) 1 / power(base, -1*exponent)
     else if (exponent == 0) 1
     else if (exponent % 2 == 1) base * power(base, exponent-1)
     else {
       var exp= exponent / 2
       var y = power(base,exp)
       y*y
     }
   }
  
  def main(args: Array[String]): Unit = {
    var ans = recursiveProduct("Hello")
    println(ans)
  
    var ans1 = product("Hello")
    println(ans1)
    
    println(ans == ans1)
    
    
    println(1/power(2, 3))
    val birth = date"${1994}-${8}-${12}"
    print(s"my birth is on $birth")
  }
}