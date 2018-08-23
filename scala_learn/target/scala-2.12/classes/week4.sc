import scala.collection.immutable.List



def isort(l: scala.collection.immutable.List[Int]): scala.collection.immutable.List[Int] =
 l match {
   case scala.collection.immutable.List() => scala.collection.immutable.List()
   case x :: xs => sort(x, isort(xs))
  }

def sort(x: Int, xs: scala.collection.immutable.List[Int]): scala.collection.immutable.List[Int] =
xs match {
  case scala.collection.immutable.List() => scala.collection.immutable.List(x)
  case y :: ys => if (x <= y) x :: xs else sort(x, ys)
}


val l = scala.collection.immutable.List(4,2,3,1)

isort(l) foreach println

val tl = scala.collection.immutable.List(1,2,3)

tl.foldLeft(scala.collection.immutable.List[Int]())((l, i)=> l ::: scala.collection.immutable.List(i*3))

scala.collection.immutable.List(1,2,3) ::: scala.collection.immutable.List(4,5,6)