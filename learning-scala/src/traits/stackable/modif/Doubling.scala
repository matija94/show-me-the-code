package traits.stackable.modif

trait Doubling extends IntQueue{
  abstract override def put(x: Int) = {println(x);super.put(2*x)}
}