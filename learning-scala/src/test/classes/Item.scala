package test.classes

import scala.collection.mutable.ArrayBuffer

abstract class Item {
  def price: Double
  def description: String
}

class SimpleItem(override val price: Double, override val description: String) extends Item {
  
}
object SimpleItem {
  def apply(price: Double, description: String): SimpleItem = new SimpleItem(price, description)
}

class Bundle(val items: ArrayBuffer[Item], override val description: String = "Bundle of items") extends Item {
  override def price: Double = items.foldLeft(0.0) { (a,z) =>
    a + z.price  
  }
  def +=(item: Item) = items += item
}
