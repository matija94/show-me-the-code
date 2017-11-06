package traits.stackable.modif

abstract class IntQueue {
  
  def get(): Int
  
  def put(x: Int) { println(x);}
}