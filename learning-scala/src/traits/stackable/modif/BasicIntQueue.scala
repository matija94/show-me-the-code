package traits.stackable.modif

import scala.collection.mutable.ArrayBuffer

class BasicIntQueue extends IntQueue {
  
  private val buf = new ArrayBuffer[Int]
  
  def get(): Int = {
    buf.remove(0)
  }

  override def put(x: Int): Unit = {
    super.put(x)
    buf += x
  }
}