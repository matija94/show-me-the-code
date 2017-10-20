package test.classes

import scala.beans.BeanProperty

class Counter {
  // generated java class will have private getters and setters
  // if modifier for instance variable is private
  // otherwise 'var value=0' will be generated as private instance variable in java class but having public setters and getters
  private var value = 0
  def increment():Unit = { value += 1}
  def current = value

  // since this is val and val is final setter for it will not be generated in java class
  val maximum_value = 150

  def settableValue = value // simple getter () is omitted since this function doesn't take any args
  
  def settableValue_=(newval: Int) {
    if(newval>value && newval<=maximum_value) value=newval // can't get any lower
  }
  
  //scala permits this as well
  
  def isEqual(other: Counter): Boolean = {
    other.value == this.value
  }
  
  // although we can make private fields for only this instance of object with type of class(in this example Counter)
  // this type of the instace varible is called object-private
  private[this] var selfish = 0

  /*def cannotcompile(other: Counter) {
    print(other.selfish)
  }*/
  
  //field annotated with @BeanProperty will tell scala interpreter to generate java getters and setters plus getter and setter in scala fashion property and property_=
  
  @BeanProperty var str_property: String = _ 
  @BeanProperty var int_property: Int = _
  
  
  // constructors in scala
  
  // auxiliary constructors
  def this(str_property: String) {
    this() // calls primary constructor
    this.str_property = str_property
  }
  
  def this(str_property: String, int_property: Int) {
    this(str_property)
    this.int_property = int_property
  }
  
}