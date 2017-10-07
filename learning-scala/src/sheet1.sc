object sheet1 {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
	
	val answer = 2+5                          //> answer  : Int = 7
	answer*0.5                                //> res0: Double = 3.5
	val ans = ("Hello, " + answer).toLowerCase//> ans  : String = hello, 7
	ans.toUpperCase()                         //> res1: String = HELLO, 7
	
	var reassing = "Var can be reassigned, unlike val"
                                                  //> reassing  : String = Var can be reassigned, unlike val
	reassing = "Reassigned"
	println(reassing)                         //> Reassigned
	
	
	val greeting, message: String = null      //> greeting  : String = null
                                                  //| message  : String = null
	reassing.intersect("matija")              //> res2: String = ai

	// numerical to string and counterwise
	val decimal = 2.66                        //> decimal  : Double = 2.66
	decimal.toInt                             //> res3: Int = 2
	decimal.toString                          //> res4: String = 2.66
	"5.33".toDouble                           //> res5: Double = 5.33
	

	3 + 5                                     //> res6: Int(8) = 8
	3.+(5)                                    //> res7: Int(8) = 8

	val num: BigInt = 125                     //> num  : BigInt = 125
	num./%(15)                                //> res8: (scala.math.BigInt, scala.math.BigInt) = (8,5)
	num /% 15                                 //> res9: (scala.math.BigInt, scala.math.BigInt) = (8,5)

	BigInt.probablePrime(3, scala.util.Random)//> res10: scala.math.BigInt = 7
	var me = "Matija"                         //> me  : String = Matija
	// char At
	me(1)                                     //> res11: Char = a
	// or
	me.apply(1)                               //> res12: Char = a
	// or get the last
	me(me.length-1)                           //> res13: Char = a
	
	// take first three
	me.take(3)                                //> res14: String = Mat
	
	// take last three
	me.takeRight(3)                           //> res15: String = ija
	// take all after first three
	me.drop(3)                                //> res16: String = ija
	// take all after last three
	me.dropRight(3)                           //> res17: String = Mat
	
	Array.apply(1,2,3,4,5)                    //> res18: Array[Int] = Array(1, 2, 3, 4, 5)
	
	Array(2,3,4,5,6)                          //> res19: Array[Int] = Array(2, 3, 4, 5, 6)

	"Matija Lukovic".count(_.isUpper)         //> res20: Int = 2
	"abcdef".containsSlice('a'.to('g'))       //> res21: Boolean = false
	"abcdef".containsSlice('a'.to('f'))       //> res22: Boolean = true


	var three_sqr = Math.sqrt(3)              //> three_sqr  : Double = 1.7320508075688772
	Math.pow(three_sqr, 2)                    //> res23: Double = 2.9999999999999996

	"crazy".*(3)                              //> res24: String = crazycrazycrazy

	10 max 2                                  //> res25: Int = 10
	BigInt(2).pow(1024)                       //> res26: scala.math.BigInt = 179769313486231590772930519078902473361797697894
                                                  //| 230657273430081157732675805500963132708477322407536021120113879871393357658
                                                  //| 789768814416622492847430639474124377767893424865485276302219601246094119453
                                                  //| 082952085005768838150682342462881473913110540827237163350510684586298239947
                                                  //| 245938479716304835356329624224137216
  import math.BigInt._
  import util.Random
  probablePrime(100, Random)                      //> res27: scala.math.BigInt = 1100244092865073097204506240841
	
	//base 36
	var rand: BigInt = Random.nextInt(5000)+1 //> rand  : BigInt = 2649
	rand.toString(36)                         //> res28: String = 21l
	
	
	// work with java classes
	var s = new java.lang.String("Matija")    //> s  : String = Matija
	
	// everything in scala has a value so does if statement..
	val z = if (s.count(_.isUpper)>=1) "more than one" else "less than one"
                                                  //> z  : String = more than one
	// void in java is Unit in scala
	// Unit in scala has just one value = () which signifies no value
	// for example
	var void = if (1>2) "True"                //> void  : Any = ()
	// is equal as
	void = if(1>2) "True" else ()
	println(void)                             //> ()

	
	val test = if(true){val r = 3+5;r-1}      //> test  : AnyVal = 7
	
	import scala.math._
	val distance = {val dx = 5-3; val dy = 6-2; sqrt(pow(dx,2) + pow(dy,2))}
                                                  //> distance  : Double = 4.47213595499958
	var name = "Matija"                       //> name  : String = Matija
	var sex = "Male"                          //> sex  : String = Male
	var age = 22                              //> age  : Int = 22

	// this method of printing is safer than printf. Compiler reports an error if u use %f with non numeric type
	print(f"Hello, my name is $name. I am $age%7.2f years old $sex.%n")
                                                  //> Hello, my name is Matija. I am   22.00 years old Male.
	val range = 1.to(5)                       //> range  : scala.collection.immutable.Range.Inclusive = Range 1 to 5
	var n: Int = 0                            //> n  : Int = 0
	// iterate while
	while(n<range.length) {
		println(range(n))
	  n+=1
	}                                         //> 1
                                                  //| 2
                                                  //| 3
                                                  //| 4
                                                  //| 5
	// iterate for
  for(i <- 1 to 5) {
  	println(i)                                //> 1
                                                  //| 2
                                                  //| 3
                                                  //| 4
                                                  //| 5
  }
  
  
  // for in a for
  for(i <- 1 to 3; j <- 1 to 3) print(f"$i$j%n")  //> 11
                                                  //| 12
                                                  //| 13
                                                  //| 21
                                                  //| 22
                                                  //| 23
                                                  //| 31
                                                  //| 32
                                                  //| 33

	// lets ommit same numbers
  for (i <-1 to 3; j <- 1 to 3 if(i!=j)) print(f"$i$j%n")
                                                  //> 12
                                                  //| 13
                                                  //| 21
                                                  //| 23
                                                  //| 31
                                                  //| 32
                                                  
  ('a'+1).toChar                                  //> res29: Char = b


	for (i <- 10 to 0 by -1) print(f"$i%n")   //> 10
                                                  //| 9
                                                  //| 8
                                                  //| 7
                                                  //| 6
                                                  //| 5
                                                  //| 4
                                                  //| 3
                                                  //| 2
                                                  //| 1
                                                  //| 0
	



	val t = "Hello"                           //> t  : String = Hello
	var t_ans: Long = 1                       //> t_ans  : Long = 1
	for (c <- t) t_ans *= c.toInt
	println(t_ans)                            //> 9415087488


	t.foldLeft(1L)(_ * _.toInt)               //> res30: Long = 9415087488


	"a".substring(1)                          //> res31: String = ""
}