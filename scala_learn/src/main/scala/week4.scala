abstract class IdealizedBoolean {

  def ifThenElse[T](t: => T, e: => T): T

  override def toString: String

  def && (bool: IdealizedBoolean): IdealizedBoolean = ifThenElse(bool, ideal_false)

  def || (bool: IdealizedBoolean): IdealizedBoolean = ifThenElse(ideal_true, bool)

  def unary_! : IdealizedBoolean = ifThenElse(ideal_false, ideal_true)

  def == (bool: IdealizedBoolean): IdealizedBoolean = ifThenElse(bool, !bool)

  def != (bool: IdealizedBoolean): IdealizedBoolean = ifThenElse(!bool, bool)

  def < (bool: IdealizedBoolean): IdealizedBoolean = ifThenElse(ideal_false, bool)
}

object ideal_true extends IdealizedBoolean {
  override def ifThenElse[T](t: => T, e: => T): T = t

  override def toString: String = "true"
}

object ideal_false extends IdealizedBoolean {
  override def ifThenElse[T](t: => T, e: => T): T = e

  override def toString: String = "false"
}



abstract class Nat {
  def isZero: Boolean
  def predecessor: Nat
  def successor: Nat = new Succ(this)
  def + (that: Nat): Nat
  def - (that: Nat): Nat
}

object Zero extends Nat {

  override def isZero: Boolean = true

  override def predecessor: Nat = throw new NoSuchElementException

  override def +(that: Nat): Nat = that

  override def -(that: Nat): Nat = if (that.isZero) this else throw new NoSuchElementException
}

class Succ(n: Nat) extends Nat {
  override def isZero: Boolean = false

  override def predecessor: Nat = n

  override def +(that: Nat): Nat = new Succ(n + that)

  override def -(that: Nat): Nat = if(that.isZero) this else new Succ(n - that.predecessor)
}

trait MyList[+T] {
  def isEmpty: Boolean
  def head: T
  def tail: MyList[T]
  def :: [U >: T](elem: U): MyList[U] = new Cons(elem, this)
  def foreach [U >: T] (f: U => Unit) = {
    def loop(list: MyList[U]): Unit = {
      if (!list.isEmpty) {
        f(list.head)
        loop(list.tail)
      }
    }
    loop(this)
  }
}

object Nil extends MyList[Nothing] {
  override def isEmpty: Boolean = true

  override def head: Nothing = ???

  override def tail: MyList[Nothing] = ???
}

class Cons[T](val head: T, val tail: MyList[T]) extends MyList[T] {
  override def isEmpty: Boolean = false
}


object MyList {
  def apply[T](first: T, second: T): MyList[T] = new Cons(first, new Cons(second, Nil))
  def apply[T](e: T):MyList[T] = new Cons(e, Nil)
  def apply[T](): MyList[T] = Nil
}
object square extends (Int => Int) {
  def apply(x: Int): Int = x * x
}

trait Expression {
  def eval: Int = this match {
    case Number(n) => n
    case Sum(e1, e2) => e1.eval + e2.eval
  }
  def show: String = this match {
    case Number(n) => n.toString
    case Var(i) => i
    case Sum(e1, e2) => e1.show + " + " + e2.show
    case Prod(e1, e2) => e1.productOpShow + " * " + e2.productOpShow
  }

  def productOpShow: String = this match {
    case Number(n) => n.toString
    case Var(i) => i
    case Sum(e1, e2) => "(" + e1.show + " + " + e2.show + ")"
    case Prod(e1, e2) => e1.productOpShow + " * " + e2.productOpShow
  }
}
case class Number(n: Int) extends Expression
case class Var(i: String) extends Expression
case class Sum(left: Expression, right: Expression) extends Expression
case class Prod(left: Expression, right: Expression) extends Expression

object Main {

  square(5)
  println(ideal_true && ideal_false)
  println(ideal_true || ideal_false)
  println(!ideal_true)
  println(ideal_false == !ideal_true)
  println(ideal_false < ideal_true)


  val one = Zero.successor
  println(one + one.successor)


  val e: Expression = Prod(Sum(Number(2), Var("x")), Var("y"))
  val e1: Expression = Sum(Prod(Number(2), Var("x")), Var("y"))
  println(e.show)
  println(e1.show)


  import scala.collection.immutable.List



  def isort(l: scala.collection.immutable.List[Int]): scala.collection.immutable.List[Int] =
    l match {
      case scala.collection.immutable.List() => scala.collection.immutable.List()
      case x :: xs => sort(x, isort(xs))
    }

  def sort(x: Int, xs: scala.collection.immutable.List[Int]): scala.collection.immutable.List[Int] =
    xs match {
      case scala.collection.immutable.List() => scala.collection.immutable.List(x)
      case y :: ys => if (x <= y) x :: xs else y :: sort(x, ys)
    }


  val l = scala.collection.immutable.List(4,2,3,1)

  isort(l) foreach println


}