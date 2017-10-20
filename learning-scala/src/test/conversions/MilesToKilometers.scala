package test.conversions

object MilesToKilometers extends UnitConversion {
  def apply(miles: Double): Double = {
    miles*1.6
  }
}