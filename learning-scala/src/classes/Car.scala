package classes

class Car(private val _manufacturer: String, private val _modelName: String) {

  var licensePlate: String = ""
  private var modelYear: Int = -1

  def this(manufacturer: String, modelName: String, modelYear: Int) {
    this(manufacturer, modelName)
    this.modelYear = modelYear
  }

  def this(manufacturer: String, modelName: String, licensePlate: String) {
    this(manufacturer, modelName)
    this.licensePlate = licensePlate
  }

  def this(manufacturer: String, modelName: String, licensePlate: String, modelYear: Int) {
    this(manufacturer, modelName, modelYear)
    this.licensePlate = licensePlate
  }

  def manufacturer: String = _manufacturer
  def model_year: Int = modelYear
  def model_name: String = _modelName

}

object Car {
  def apply(manufacturer: String, modelName: String): Car = {
    new Car(manufacturer, modelName)
  }
}

object Demo extends App {

  val c: Car = Car("AUDI", "A5")
  println(c.manufacturer)
  println(c.model_name)
  c.licensePlate = "123ABZER344"

  println(c.licensePlate)
}