package traits.stackable.modif

trait Incrementing extends IntQueue{
  abstract override def put(x: Int) = {println(x);super.put(x+1)}
}