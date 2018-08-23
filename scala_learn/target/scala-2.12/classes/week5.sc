import scala.collection.immutable.List

def last[T](xs: List[T]): T = xs match {
  case List() => throw new IllegalArgumentException
  case List(x) => x
  case _ :: ys => last(ys)
}

def init[T](xs: List[T]): List[T] = xs match {
  case List() => throw new IllegalArgumentException
  case List(_) => List()
  case y :: ys => y :: init(ys)
}

def concat[T](xs: List[T], ys: List[T]): List[T] = xs match {
  case List() => ys
  case z :: zs => z :: concat(zs, ys)
}

def reverse[T](xs: List[T]): List[T] = xs match {
  case List() => List()
  case y :: ys => concat(reverse(ys), List(y))
}

def removeAt[T](n:Int, xs: List[T]): List[T] = {
  def loop(i: Int, acc: List[T]): List[T] = {
    if (i == xs.length) acc
    else if (i != n) loop(i+1, acc ::: List(xs(i)))
    else loop(i+1, acc)
  }
  loop(0, List())
}


// TODO impl flatten
def flatten(xs: List[Any]): List[Any] = ???


def mergeSort[T](l: List[T])(implicit ord: Ordering[T]): List[T] = {
  def merge(x: List[T], y: List[T]): List[T] = (x, y) match {
    case (List(), y) => y
    case (x, List()) => x
    case (xh :: xt, yh :: yt) => {
      if (ord.gt(yh, xh)) xh :: merge(xt, y)
      else yh :: merge(x, yt)
    }
  }
  val n = l.length/2
  if (n == 0) l
  else {
    val (left, right) = l splitAt n
    merge(mergeSort(left), mergeSort(right))
  }
}


def squareList(l: List[Int]): List[Int] = l map (x=>x*x)

def squareListPatternMatch(l: List[Int]): List[Int] = l match {
  case List() => List()
  case x :: xs => x*x :: squareListPatternMatch(xs)
}

def packs[T](l: List[T]): List[List[T]] = l match {
  case List() => List()
  case x :: xs =>
    val (first, rest) = l span (s => s == x)
    first :: packs(rest)
}

def encode[T](l: List[T]): List[(T, Int)] = packs(l) map(subl => (subl.head, subl.length))



def reduceLeft[T](l: List[T], op: (T, T) => T): T = l match {
  case List() => throw new IllegalArgumentException("cannot reduce on empty list")
  case x :: xs => myFoldLeft(x)(xs, op)
}

def myFoldLeft[U, T](unit: U)(l: List[T], op: (U, T) => U): U = l match {
  case List() => unit
  case x :: _ => myFoldLeft(op(unit, x))(l.tail, op)
}

def myFoldRight[U, T](unit: U)(l: List[T], op: (U, T) => U): U = l match {
  case List() => unit
  case x :: _ => op(x, myFoldRight(unit)(l.tail, op))
}

init(List(1,2,3))
concat(List(1,2), List(3,4))
reverse(List(1,2,3,4))
removeAt(1, List(1,2,3,4))
//flatten(List(List(1, 1), 2, List(3, List(5, 8))))

mergeSort(List(4,5,2,1,3,10,6,9,8,7))

squareListPatternMatch(List(2,4,6))


packs(List("a", "a", "a", "b", "b", "c", "c", "a"))
encode(List("a", "a", "a", "b", "b", "c", "c", "a"))


reduceLeft(List(1,2,3,4,5), (x: Int ,y: Int) => x*y)
myFoldRight(1)(List(1,2,3,4,5), (x: Int, y: Int) => x*y)