import java.util.NoSuchElementException

abstract class IntSet {
  def incl(x: Int): IntSet
  def contains(x: Int): Boolean
  def union(other: IntSet): IntSet
}

object EmptySet extends IntSet {
  override def contains(x: Int): Boolean = false
  override def incl(x: Int): IntSet = new NonEmptySet(x, EmptySet, EmptySet)
  override def union(other: IntSet): IntSet = other

  override def toString: String = "."
}

class NonEmptySet(elem: Int, left: IntSet, right: IntSet) extends IntSet {

  override def contains(x: Int): Boolean = {
    if (x < elem) left contains x
    else if (x > elem) right contains x
    else true
  }

  override def incl(x: Int): IntSet = {
    if (x < elem) new NonEmptySet(elem, left incl x, right)
    else if (x > elem) new NonEmptySet(elem, left, right incl x)
    else this
  }

  override def union(other: IntSet): IntSet = {
    println(elem)
    ((left union right) union other) incl elem
  }

  override def toString: String = "{" + left + elem + right + "}"
}

val root = new NonEmptySet(7, EmptySet, EmptySet)
val root1 = root incl 4 incl 10

val root2 = new NonEmptySet(11, EmptySet, EmptySet)
val root3 = root2 incl 5 incl 12

root1 union root3

trait List[T] {
  def isEmpty: Boolean
  def head: T
  def tail: List[T]
}

class Cons[T](val head: T, val tail: List[T]) extends List[T] {
  override def isEmpty = false
}
class Nil[T] extends List[T] {
  override def isEmpty: Boolean = true
  override def head: Nothing = throw new NoSuchElementException("Nil.head")
  override def tail: Nothing = throw new NoSuchElementException("Nil.head")
}

def nth[T](l: List[T], n: Int): T = {
    if (l.isEmpty ||  n < 0) throw new IndexOutOfBoundsException()
    else if (n == 0) l.head
    else nth(l.tail, n-1)
}

val l = new Cons(10, new Cons(9, new Cons(8, new Nil)))
nth(l, 2)
