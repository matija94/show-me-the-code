object sheet1 {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(59); 
  println("Welcome to the Scala worksheet");$skip(20); 
	
	val answer = 2+5;System.out.println("""answer  : Int = """ + $show(answer ));$skip(12); val res$0 = 
	answer*0.5;System.out.println("""res0: Double = """ + $show(res$0));$skip(44); 
	val ans = ("Hello, " + answer).toLowerCase;System.out.println("""ans  : String = """ + $show(ans ));$skip(19); val res$1 = 
	ans.toUpperCase();System.out.println("""res1: String = """ + $show(res$1));$skip(54); 
	
	var reassing = "Var can be reassigned, unlike val";System.out.println("""reassing  : String = """ + $show(reassing ));$skip(25); 
	reassing = "Reassigned";$skip(19); 
	println(reassing);$skip(42); 
	
	
	val greeting, message: String = null;System.out.println("""greeting  : String = """ + $show(greeting ));System.out.println("""message  : String = """ + $show(message ));$skip(30); val res$2 = 
	reassing.intersect("matija");System.out.println("""res2: String = """ + $show(res$2));$skip(61); 

	// numerical to string and counterwise
	val decimal = 2.66;System.out.println("""decimal  : Double = """ + $show(decimal ));$skip(15); val res$3 = 
	decimal.toInt;System.out.println("""res3: Int = """ + $show(res$3));$skip(18); val res$4 = 
	decimal.toString;System.out.println("""res4: String = """ + $show(res$4));$skip(17); val res$5 = 
	"5.33".toDouble;System.out.println("""res5: Double = """ + $show(res$5));$skip(10); val res$6 = 
	

	3 + 5;System.out.println("""res6: Int(8) = """ + $show(res$6));$skip(8); val res$7 = 
	3.+(5);System.out.println("""res7: Int(8) = """ + $show(res$7));$skip(24); 

	val num: BigInt = 125;System.out.println("""num  : BigInt = """ + $show(num ));$skip(12); val res$8 = 
	num./%(15);System.out.println("""res8: (scala.math.BigInt, scala.math.BigInt) = """ + $show(res$8));$skip(11); val res$9 = 
	num /% 15;System.out.println("""res9: (scala.math.BigInt, scala.math.BigInt) = """ + $show(res$9));$skip(45); val res$10 = 

	BigInt.probablePrime(3, scala.util.Random);System.out.println("""res10: scala.math.BigInt = """ + $show(res$10));$skip(19); 
	var me = "Matija";System.out.println("""me  : String = """ + $show(me ));$skip(19); val res$11 = 
	// char At
	me(1);System.out.println("""res11: Char = """ + $show(res$11));$skip(20); val res$12 = 
	// or
	me.apply(1);System.out.println("""res12: Char = """ + $show(res$12));$skip(37); val res$13 = 
	// or get the last
	me(me.length-1);System.out.println("""res13: Char = """ + $show(res$13));$skip(35); val res$14 = 
	
	// take first three
	me.take(3);System.out.println("""res14: String = """ + $show(res$14));$skip(39); val res$15 = 
	
	// take last three
	me.takeRight(3);System.out.println("""res15: String = """ + $show(res$15));$skip(43); val res$16 = 
	// take all after first three
	me.drop(3);System.out.println("""res16: String = """ + $show(res$16));$skip(47); val res$17 = 
	// take all after last three
	me.dropRight(3);System.out.println("""res17: String = """ + $show(res$17));$skip(26); val res$18 = 
	
	Array.apply(1,2,3,4,5);System.out.println("""res18: Array[Int] = """ + $show(res$18));$skip(20); val res$19 = 
	
	Array(2,3,4,5,6);System.out.println("""res19: Array[Int] = """ + $show(res$19));$skip(36); val res$20 = 

	"Matija Lukovic".count(_.isUpper);System.out.println("""res20: Int = """ + $show(res$20));$skip(37); val res$21 = 
	"abcdef".containsSlice('a'.to('g'));System.out.println("""res21: Boolean = """ + $show(res$21));$skip(37); val res$22 = 
	"abcdef".containsSlice('a'.to('f'));System.out.println("""res22: Boolean = """ + $show(res$22));$skip(32); 


	var three_sqr = Math.sqrt(3);System.out.println("""three_sqr  : Double = """ + $show(three_sqr ));$skip(24); val res$23 = 
	Math.pow(three_sqr, 2);System.out.println("""res23: Double = """ + $show(res$23));$skip(15); val res$24 = 

	"crazy".*(3);System.out.println("""res24: String = """ + $show(res$24));$skip(11); val res$25 = 

	10 max 2;System.out.println("""res25: Int = """ + $show(res$25));$skip(21); val res$26 = 
	BigInt(2).pow(1024)
  import math.BigInt._
  import util.Random;System.out.println("""res26: scala.math.BigInt = """ + $show(res$26));$skip(73); val res$27 = 
  probablePrime(100, Random);System.out.println("""res27: scala.math.BigInt = """ + $show(res$27));$skip(56); 
	
	//base 36
	var rand: BigInt = Random.nextInt(5000)+1;System.out.println("""rand  : BigInt = """ + $show(rand ));$skip(19); val res$28 = 
	rand.toString(36);System.out.println("""res28: String = """ + $show(res$28));$skip(71); 
	
	
	// work with java classes
	var s = new java.lang.String("Matija");System.out.println("""s  : String = """ + $show(s ));$skip(134); 
	
	// everything in scala has a value so does if statement..
	val z = if (s.count(_.isUpper)>=1) "more than one" else "less than one";System.out.println("""z  : String = """ + $show(z ));$skip(145); 
	// void in java is Unit in scala
	// Unit in scala has just one value = () which signifies no value
	// for example
	var void = if (1>2) "True";System.out.println("""void  : Any = """ + $show(void ));$skip(47); 
	// is equal as
	void = if(1>2) "True" else ();$skip(15); 
	println(void);$skip(41); 

	
	val test = if(true){val r = 3+5;r-1}
	
	import scala.math._;System.out.println("""test  : AnyVal = """ + $show(test ));$skip(97); 
	val distance = {val dx = 5-3; val dy = 6-2; sqrt(pow(dx,2) + pow(dy,2))};System.out.println("""distance  : Double = """ + $show(distance ));$skip(21); 
	var name = "Matija";System.out.println("""name  : String = """ + $show(name ));$skip(18); 
	var sex = "Male";System.out.println("""sex  : String = """ + $show(sex ));$skip(14); 
	var age = 22;System.out.println("""age  : Int = """ + $show(age ));$skip(180); 

	// this method of printing is safer than printf. Compiler reports an error if u use %f with non numeric type
	print(f"Hello, my name is $name. I am $age%7.2f years old $sex.%n");$skip(21); 
	val range = 1.to(5);System.out.println("""range  : scala.collection.immutable.Range.Inclusive = """ + $show(range ));$skip(16); 
	var n: Int = 0;System.out.println("""n  : Int = """ + $show(n ));$skip(74); 
	// iterate while
	while(n<range.length) {
		println(range(n))
	  n+=1
	};$skip(51); 
	// iterate for
  for(i <- 1 to 5) {
  	println(i)
  };$skip(77); 
  
  
  // for in a for
  for(i <- 1 to 3; j <- 1 to 3) print(f"$i$j%n");$skip(87); 

	// lets ommit same numbers
  for (i <-1 to 3; j <- 1 to 3 if(i!=j)) print(f"$i$j%n");$skip(68); val res$29 = 
                                                  
  ('a'+1).toChar;System.out.println("""res29: Char = """ + $show(res$29));$skip(43); 


	for (i <- 10 to 0 by -1) print(f"$i%n");$skip(22); 
	



	val t = "Hello";System.out.println("""t  : String = """ + $show(t ));$skip(21); 
	var t_ans: Long = 1;System.out.println("""t_ans  : Long = """ + $show(t_ans ));$skip(31); 
	for (c <- t) t_ans *= c.toInt;$skip(16); 
	println(t_ans);$skip(31); val res$30 = 


	t.foldLeft(1L)(_ * _.toInt);System.out.println("""res30: Long = """ + $show(res$30));$skip(20); val res$31 = 


	"a".substring(1);System.out.println("""res31: String = """ + $show(res$31))}
}
