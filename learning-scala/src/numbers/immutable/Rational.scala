package numbers.immutable

class Rational(num: Int, denom: Int) extends Ordered[Rational] {
	require(denom != 0)
	private val g: Int = gcd(num.abs,denom.abs)
  val n: Int = num/g
  val d: Int = denom/g

  def this(n: Int) = this(n,1)

  def max(r: Rational): Rational = if(this < r) r else this
  
  def +(r: Rational): Rational = new Rational(n * r.d + r.n * d, d*r.d)
  
  def +(i: Int): Rational = new Rational(n + d*i,d)
  
  def -(r: Rational): Rational = new Rational(n*r.d-d*r.n,d*r.d)
  
  def -(i: Int): Rational = new Rational(n-i*d,d)
  
  def *(r: Rational): Rational = new Rational(n*r.n,d*r.d)
  
  def *(i: Int): Rational = new Rational(n*i,d)
  
  def /(r: Rational): Rational = new Rational(n*r.denominator,d*r.n)
  
  def /(i: Int): Rational = new Rational(n, d*i) 
  
  override def toString: String = s"$n/$d"

  def numerator: Int = n
  
  def denominator: Int = d
  
  def toDouble: Double = n.toDouble/d.toDouble
  
  def compare(that: Rational): Int = {
	  (this.n * that.d)-(that.n * this.d)
	}
  
  private def gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a%b)

}