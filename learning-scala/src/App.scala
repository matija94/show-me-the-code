import conversions.InchesToCentimeters
import conversions.MilesToKilometers
import conversions.GalonsToLitres
import conversions.Units
import algorithms.maps.MultiMap
import scala.collection.mutable.ArrayBuffer
import classes.Bundle
import classes.SimpleItem


object App extends App{
  
  println(InchesToCentimeters(54.5))
  
  println(MilesToKilometers(100))
  
  println(GalonsToLitres(10))
  
  for(unit<-Units.values) println(unit, unit.id)
  
  
  
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
  
  
  
}