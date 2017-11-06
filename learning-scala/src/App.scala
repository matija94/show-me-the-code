import algorithms.maps.MultiMap
import scala.collection.mutable.ArrayBuffer
import test.classes.Bundle
import test.classes.SimpleItem
import numbers.immutable.Rational
import traits.stackable.modif.BasicIntQueue
import traits.stackable.modif.Doubling
import traits.stackable.modif.Filtering
import traits.stackable.modif.Incrementing
import linearization.Cat


object App extends App{
  
  val multiMap: MultiMap[Int, String] = new MultiMap[Int, String]
  
  multiMap += (2, "Matija")
  multiMap +=(2, "Jana")
  multiMap += (2, "Sneza")
  
  multiMap += (5, "Ckilim")
  
  for(e<-multiMap(2)) println(e)

  for((k,v) <-multiMap) println(s"$k=$v")



  val bundle: Bundle = new Bundle(ArrayBuffer(SimpleItem(15.0,"Toy"), SimpleItem(25.0, "Advanced Toy"), SimpleItem(50.0, "Expensive Toy")))
  bundle += SimpleItem(3.5, "Poor Toy")
  println(bundle price)
  
  
  
  val r = new Rational(2,4)
  
  println(r * new Rational(1,2))
  println(r / new Rational(1,2))
  
  println((r - 5).toDouble)
  
  
  println((r+5).toDouble)
  
  val third = new Rational(1,3)
  
  println(r<third)
  println(r>third)
  
  
  val queue = new BasicIntQueue with Doubling with Incrementing
  queue put 15

  val cat = new Cat
  cat.test
}