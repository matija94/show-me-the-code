import scala.collection.mutable.ArrayBuffer
import java.util.ArrayList

object arrays {



	val nums = new Array[Int](10)             //> nums  : Array[Int] = Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
	
	
	val strs = new Array[String](10)          //> strs  : Array[String] = Array(null, null, null, null, null, null, null, null
                                                  //| , null, null)
	// no new when passing initial values, type is inferred
	val s_init = Array("Matija", "Lukovic")   //> s_init  : Array[String] = Array(Matija, Lukovic)

	s_init(0) = "Ckilima"

	// Array doesn't grow in scala , it's java equivalent is []
	
	// ArrayBuffer is dynamic and grows like ArrayList in java
	
	val ints = new ArrayBuffer[Int]()         //> ints  : scala.collection.mutable.ArrayBuffer[Int] = ArrayBuffer()
	// += adds an element to the tail of the array
	ints += (1,2,3,4,5)                       //> res0: arrays.ints.type = ArrayBuffer(1, 2, 3, 4, 5)

	// add another collection to array
	ints ++= Array(10,11,12,13)               //> res1: arrays.ints.type = ArrayBuffer(1, 2, 3, 4, 5, 10, 11, 12, 13)
	
	// remove last two
	ints.trimEnd(2)

	// we could insert elements as well
	ints.insert(1,15)
	
	// or insert multiple
	ints.insert(0,101,102,103)


	// we can remove elements
	ints.remove(0)                            //> res2: Int = 101
	println(ints)                             //> ArrayBuffer(102, 103, 1, 15, 2, 3, 4, 5, 10, 11)
	// or tell how much elements to remove including element on index
	ints.remove(0,3)
	println(ints)                             //> ArrayBuffer(15, 2, 3, 4, 5, 10, 11)


	val arr = ints.toArray                    //> arr  : Array[Int] = Array(15, 2, 3, 4, 5, 10, 11)

	println(ints == arr.toBuffer)             //> true


	for(i <- 0 until ints.length) println(s"$i : ${ints(i)}")
                                                  //> 0 : 15
                                                  //| 1 : 2
                                                  //| 2 : 3
                                                  //| 3 : 4
                                                  //| 4 : 5
                                                  //| 5 : 10
                                                  //| 6 : 11


	// until is same as to excepts it excludes last value
	for (i <- ints.length-1 until -1 by -1) println(s"$i")
                                                  //> 6
                                                  //| 5
                                                  //| 4
                                                  //| 3
                                                  //| 2
                                                  //| 1
                                                  //| 0

	for (i <- ints.indices) println(s"$i")    //> 0
                                                  //| 1
                                                  //| 2
                                                  //| 3
                                                  //| 4
                                                  //| 5
                                                  //| 6

	for (i <- ints.indices.reverse) println(s"$i")
                                                  //> 6
                                                  //| 5
                                                  //| 4
                                                  //| 3
                                                  //| 2
                                                  //| 1
                                                  //| 0

	// same as python list comprehension
	val a = Array(2,4,6,8)                    //> a  : Array[Int] = Array(2, 4, 6, 8)
	val squared_a = for(e <- a) yield e*e     //> squared_a  : Array[Int] = Array(4, 16, 36, 64)

	val powers_of_two = for(e <- squared_a if (e & (e-1)) == 0) yield e
                                                  //> powers_of_two  : Array[Int] = Array(4, 16, 64)

	// get elements less than 6 from array a and multiply them by two
	a.filter(_ < 6).map(2*_)                  //> res3: Array[Int] = Array(4, 8)

	a filter {_ < 6} map {2*_}                //> res4: Array[Int] = Array(4, 8)
	// scala has sum function as python does

	a.sum                                     //> res5: Int = 20

	a.min                                     //> res6: Int = 2
	a.max                                     //> res7: Int = 8
	val reversed_a = a.reverse                //> reversed_a  : Array[Int] = Array(8, 6, 4, 2)
	// sorted yields new collections instead modifying original
	val sorted = reversed_a.sorted            //> sorted  : Array[Int] = Array(2, 4, 6, 8)

	// sort with own comparison function
	val reversed = Array(1,2,3).sortWith(_ > _)
                                                  //> reversed  : Array[Int] = Array(3, 2, 1)


	// string joiner
	reversed.mkString("[", ", ", "]")         //> res8: String = [3, 2, 1]



	// create a matrix 3 rows 4 columns
	
	val matrix = Array.ofDim[Double](3, 4)    //> matrix  : Array[Array[Double]] = Array(Array(0.0, 0.0, 0.0, 0.0), Array(0.0
                                                  //| , 0.0, 0.0, 0.0), Array(0.0, 0.0, 0.0, 0.0))


	import scala.collection.JavaConversions.bufferAsJavaList
	val test = ArrayBuffer("matija", "lukovic")
                                                  //> test  : scala.collection.mutable.ArrayBuffer[String] = ArrayBuffer(matija, 
                                                  //| lukovic)
	val java = new ArrayList(test)            //> java  : java.util.ArrayList[String] = [matija, lukovic]
	println(java)                             //> [matija, lukovic]



	val ar = Array(1,2,3,4,5)                 //> ar  : Array[Int] = Array(1, 2, 3, 4, 5)
	ar.foldLeft(0) {(a,z) => a + z}           //> res9: Int = 15
}