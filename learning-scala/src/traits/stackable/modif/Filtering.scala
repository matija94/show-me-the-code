package traits.stackable.modif

trait Filtering extends IntQueue{
  abstract override def put(x: Int) = {println(x);if (x>0) super.put(x)}
}