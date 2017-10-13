package classes

class Person(val name: String, val age: Int=22) {
  //                        ^
  //                        |
  //                        |
  //                   primary constructor 
  
  // this statement is executed moment when client says val p = new Person("Matija",22) or val p = new Person("Matija"), note that age is defaulted to 22
  println("Just constructed new Person")
  // we can define any initial behavior for object creation here
    
  def description = s"Name = $name; Age=$age"
}