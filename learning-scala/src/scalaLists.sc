package utils

object scalaLists {
	
	// scala list is immutable
	// like strings in java, every operation returns new list
	
	val oneTwoThree = List(1,2,3)             //> oneTwoThree  : List[Int] = List(1, 2, 3)
	
	
	// concat two lists
	val concat = List(6,7) ::: oneTwoThree    //> concat  : List[Int] = List(6, 7, 1, 2, 3)
	println(concat)                           //> List(6, 7, 1, 2, 3)

	1 :: concat                               //> res0: List[Int] = List(1, 6, 7, 1, 2, 3)

	// Nil is empty list


	// we can construct lists like this

 val test = "matija" :: "lukovic" :: "  " :: "nova" :: "godina" :: Nil
                                                  //> test  : List[String] = List(matija, lukovic, "  ", nova, godina)


test.filter(e=>e.length>4)                        //> res1: List[String] = List(matija, lukovic, godina)

val ascci_values = test.map(e=>e.foldLeft(0)((aggr,curr)=> aggr+curr))
                                                  //> ascci_values  : List[Int] = List(630, 765, 64, 436, 626)

val noEmptyStrings = (test map {e=>e trim} forall {e=>e.length>0})
                                                  //> noEmptyStrings  : Boolean = false
// faster solution of above check
val hasEmpty = test exists {e=> e.trim.length == 0}
                                                  //> hasEmpty  : Boolean = true
test map {(e:String) => e.trim.length}            //> res2: List[Int] = List(6, 7, 0, 4, 6)

println(test)                                     //> List(matija, lukovic,   , nova, godina)

test reverse                                      //> res3: List[String] = List(godina, nova, "  ", lukovic, matija)

test head                                         //> res4: String = matija

test tail                                         //> res5: List[String] = List(lukovic, "  ", nova, godina)

test init                                         //> res6: List[String] = List(matija, lukovic, "  ", nova)

// sort by string length
test sortWith {(e1,e2) => e1.length > e2.length}  //> res7: List[String] = List(lukovic, matija, godina, nova, "  ")







}