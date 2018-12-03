package reductions

import scala.annotation._
import org.scalameter._
import common._

object ParallelParenthesesBalancingRunner {

  @volatile var seqResult = false

  @volatile var parResult = false

  val standardConfig = config(
    Key.exec.minWarmupRuns -> 40,
    Key.exec.maxWarmupRuns -> 80,
    Key.exec.benchRuns -> 120,
    Key.verbose -> true
  ) withWarmer(new Warmer.Default)

  def main(args: Array[String]): Unit = {
    val length = 100000000
    val chars = new Array[Char](length)
    val threshold = 10000
    val seqtime = standardConfig measure {
      seqResult = ParallelParenthesesBalancing.balance(chars)
    }
    println(s"sequential result = $seqResult")
    println(s"sequential balancing time: $seqtime ms")

    val fjtime = standardConfig measure {
      parResult = ParallelParenthesesBalancing.parBalance(chars, threshold)
    }
    println(s"parallel result = $parResult")
    println(s"parallel balancing time: $fjtime ms")
    println(s"speedup: ${seqtime / fjtime}")
  }
}

object ParallelParenthesesBalancing {

  /** Returns `true` iff the parentheses in the input `chars` are balanced.
   */
  def balance(chars: Array[Char]): Boolean = {
    val parentheses = for {
      ch <- chars
      if (ch == '(' || ch == ')')
    } yield ch

    var res = 0
    for (ch <- parentheses) {
      if (res < 0)
        return false
      else if (ch == '(')
        res += 1
      else
        res -=1
    }
    res == 0
  }

  /** Returns `true` iff the parentheses in the input `chars` are balanced.

      ((( () () () )))
    */
  def parBalance(chars: Array[Char], threshold: Int): Boolean = {

    def traverse(idx: Int, until: Int, arg1: Int, arg2: Int): Int = {
      var i = idx
      var left = arg1
      var right = arg2
      while (i < until) {
        if (chars(idx) == '(')
          left += 1
        else if (chars(idx) == ')')
          right += 1
        i += 1
      }
      left-right
    }

    def reduce(from: Int, until: Int): Int = {
      if ((until - from) <= threshold)
        traverse(from, until, 0, 0)
      else {
        val mid = (until + from) / 2
        val (l, r) = parallel(reduce(from, mid), reduce(mid, until))
        l + r
      }
    }

    reduce(0, chars.length) == 0
  }

  // For those who want more:
  // Prove that your reduction operator is associative!

}
