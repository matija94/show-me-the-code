def sumRange(f: Int => Int, a: Int, b: Int): Int = {
    def loop(a: Int, acc: Int): Int = {
        if (a > b) acc
        else loop(a+1, acc + f(a))
    }
    loop(a, 0)
}

def sumRangeCurry(f: Int => Int)(a: Int, b: Int): Int =
    mapReduce(f, (x, y) => x + y, 0)(a, b)

def factorial(a: Int): Int = product(x => x)(1, a)

def product(f: Int => Int)(a: Int, b: Int): Int =
    mapReduce(f, (x, y) => x * y, 1)(a, b)

def mapReduce(f: Int => Int, combine: (Int, Int) => Int, unit: Int)(a: Int, b: Int): Int =
    if (a > b) unit else combine(f(a), mapReduce(f, combine, unit)(a+1, b))

sumRange(x => x, 3, 7)
sumRange((x: Int) => x*x*x, 3, 7)

sumRangeCurry(x => x)(1,10)

val sumCube = sumRangeCurry(x => x*x*x)(_,_)
val sumSquare = sumRangeCurry(x => x*x)(_,_)

sumCube(3,7)
sumSquare(3,7)

factorial(4)



def fixedPoint(f: Double => Double)(guess: Double): Double = {
    val tolerance = 0.0001
    def isCloseEnough(guess: Double, result: Double): Boolean =
        Math.abs((result - guess) / result) / result < tolerance
    def iterate(guess: Double): Double = {
        if (isCloseEnough(guess, f(guess))) guess
        else iterate(f(guess))
    }
    iterate(guess)
}

def averageDamp(f: Double => Double)(x: Double): Double = (x + f(x)) / 2

def sqrt(x: Double): Double = fixedPoint(averageDamp(y => x / y))(1)

sqrt(25)


class Rational(x: Int, y: Int) {
    require(y != 0, "denominator must be nonzero")

    def this(x: Int) = this(x, 1)

    private def gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a%b)
    def numer = x / gcd(x, y)
    def denom = y / gcd(x, y)

    def + (r: Rational): Rational =
        new Rational(numer * r.denom + r.numer * denom, denom * r.denom)

    def unary_- : Rational = new Rational(-numer, denom)

    def - (r: Rational): Rational = this + -r

    def < (r: Rational): Boolean = numer * r.denom < r.numer * denom

    def max (r: Rational): Rational = if(this < r) r else this

    override def toString: String = numer + "/" + denom

}
val x = new Rational(24, 18)
x < (new Rational(5, 3))

x < (new Rational(5,4))
x max (new Rational(5,4))
x.numer
x + (new Rational(6/3)) - (new Rational(5, 3))



type Set = Int => Boolean

def singletonSet(x: Int): Set = y => x == y

def contains(s: Set, x: Int) = s(x)

def union(s: Set, t: Set): Set = y => contains(s, y) || contains(t, y)

def intersection(s: Set, t: Set): Set = y => contains(s, y) && contains(t, y)

def difference(s: Set, t: Set): Set = y => contains(s, y) && !contains(t, y)

def filter(s: Set, p: Int => Boolean): Set = y => contains(s, y) && p(y)

def forall(s: Set, p: Int => Boolean): Boolean = {
    val bound = 1000
    def iterate(x: Int): Boolean = {
        if (x > bound) return true
        else if (contains(s, x) && !p(x)) return false
        iterate(x + 1)
    }
    iterate(-bound)
}

def exists(s: Set, p: Int => Boolean): Boolean = {
    !forall(s, x => !p(x))
}

def map(s: Set, m: Int => Int): Set = {
    val bound = 1000
    def iterate(x: Int, accumulateSet: Set): Set = {
        if (x > 1000) accumulateSet
        else if (contains(s, x)) iterate(x + 1, union(accumulateSet, y => m(x) == y))
        else iterate(x + 1, accumulateSet)
    }
    iterate(-bound, _ => false)
}



val set_2_5 = union(singletonSet(2), singletonSet(5))
val set_2_5_7 = union(set_2_5, singletonSet(7))


contains(set_2_5_7, 2)
map(set_2_5_7, x => x*2)(14)



List(1,2,3)