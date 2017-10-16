package utils

object Conversions {
  
  
  def inchesToCentimeters(inches: Double): Double = {
    inches*2.54
  }
  
  def galonsToLitres(galons: Double): Double = {
    galons*3.78541
  }
  
  def milesToKilometers(miles: Double): Double = {
    miles*1.6
  }
}