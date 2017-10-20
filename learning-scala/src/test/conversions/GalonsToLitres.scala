package test.conversions

object GalonsToLitres extends UnitConversion {
  def apply(galons: Double): Double = {
    galons*3.78541
  }
}