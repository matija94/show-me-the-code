package test.classes

class Person(name: String, var age: Int=22) {
  //                        ^
  //                        |
  //                        |
  //                   primary constructor 
  
  // this statement is executed moment when client says val p = new Person("Matija",22) or val p = new Person("Matija"), note that age is defaulted to 22
  println("Just constructed new Person")
  // we can define any initial behavior for object creation here
  if (age < 0) age = 0
  private val firstName = name.split(" ")(0)  
  private val lastName = name.split(" ")(1)
  
  def description = s"Name = $firstName $lastName; Age=$age"

}