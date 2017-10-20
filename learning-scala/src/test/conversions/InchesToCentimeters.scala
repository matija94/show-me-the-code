package test.conversions

object InchesToCentimeters extends UnitConversion {
  def apply(inches: Double): Double= {
    inches*2.54
  }
}