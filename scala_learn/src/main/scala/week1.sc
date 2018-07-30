def abs(x: Double): Double = if(x>=0) x else -x


def sqrtIter(guess: Double, x: Double): Double =
  if(isGoodEnough(guess, x)) guess
  else sqrtIter(improve(guess, x),x)

def isGoodEnough(guess: Double, x: Double): Boolean =
  abs(guess * guess - x) / x < 0.001

def improve(guess: Double, x: Double): Double =
  return (guess + x/guess) / 2

def sqrt(x: Double): Double = sqrtIter(1.0, x)


def factorial(n: Int): Int = {

  def loop(acc: Int, n: Int): Int =
    if (n == 0) acc
    else loop(acc * n, n-1)
  loop(1,n)

}

def gcd(a: Int, b: Int): Int = {
  if (b == 0) a else gcd(b, a % b)
}



def pascal(c: Int, r: Int): Int = {
  if (c <= 0 || c >= r) 1
  else pascal(c-1, r-1) + pascal(c, r-1)

}

def balance(chars: List[Char]): Boolean = {
  var balance = 0
  def help(chars: List[Char]): Boolean = {
    if (chars.isEmpty) return balance == 0
    else {
      if (chars.head == '(') balance = balance + 1
      else if (chars.head == ')') {
        if (balance == 0) return false
        else balance = balance - 1
      }
      help(chars.tail)
    }
  }
  help(chars)
}

def countChange(money: Int, coins: List[Int]): Int = {
  if (money > 0 && !coins.isEmpty) {
    countChange(money, coins.tail) +
      countChange(money - coins.head, coins)
  }
  else if (money == 0) 1 else 0
}

countChange(4, List(1, 2))

//balance(List('(', '(', ')', ')'))
