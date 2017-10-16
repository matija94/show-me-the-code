package algorithms.maps

import scala.collection.mutable.Map
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.HashMap
import scala.collection.Iterator
import scala.collection.Iterable

class MultiMap[K,V] extends Iterable[(K,V)] {
  
  private val data: Map[K,ArrayBuffer[V]] = HashMap() 
  private var n: Int = 0
  
  def +=(k: K, v: V) = {
    if (data.contains(k)) {
      data(k)+=v
    }
    else {
      val buffer = new ArrayBuffer[V]
      buffer += v
      data(k)=buffer
    }
    n+=1
  }
  
  def -=(k: K): (K,V) = {
    val secondary = data(k)
    val v = secondary.remove(secondary.length-1)
    if (secondary.isEmpty) data -= k
    n -= 1
    (k,v)
  }
  
  def apply(k: K): ArrayBuffer[V]= {
    /*val secondary = data(k)
    for(v <- secondary) yield v*/
    // let client permission to mutate array
    data(k)
  }

  def iterator = new Iterator[(K, V)] {
	  val keyIterator: Iterator[K] = data.keySet.iterator
    var lastKey = keyIterator.next
    var listIterator = data(lastKey).iterator
    
    override def hasNext =  keyIterator.hasNext || listIterator.hasNext
    override def next = {
	    if(listIterator.hasNext) (lastKey,listIterator.next)
      else {
        lastKey = keyIterator.next
        listIterator = data(lastKey).iterator
        (lastKey,listIterator.next)
      }
    }
  }
}
