import scala.collection.mutable.HashMap
import scala.collection.Map
import scala.collection.immutable.HashMap

object maps {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet


	// this constructs immutable map! types inffered
	val scores = Map("Matija" -> 2, "Jana" -> 2.5, "Snezana" -> 2.5)
                                                  //> scores  : scala.collection.Map[String,AnyVal] = Map(Matija -> 2, Jana -> 2.5
                                                  //| , Snezana -> 2.5)
	
	
	// mutable map, types inffered
	val mutable_scores = scala.collection.mutable.Map("Matija" -> 3, "Jana" ->3, "Snezana" -> 3)
                                                  //> mutable_scores  : scala.collection.mutable.Map[String,Int] = Map(Snezana -> 
                                                  //| 3, Jana -> 3, Matija -> 3)
	
	// blank map needs type
	
	val mutable_scores_blank = scala.collection.mutable.Map[String,Int]()
                                                  //> mutable_scores_blank  : scala.collection.mutable.Map[String,Int] = Map()
	
	
	scores("Matija")                          //> res0: AnyVal = 2
	mutable_scores("Matija")                  //> res1: Int = 3


	if(scores.contains("none")) scores("none") else 0
                                                  //> res2: AnyVal = 0
	// better approach
	scores.getOrElse("none", 3)               //> res3: AnyVal = 3
	

	// get same map with set default for all non defined keys in the map
	val mut_scores_defaulted = mutable_scores.withDefaultValue(5)
                                                  //> mut_scores_defaulted  : scala.collection.mutable.Map[String,Int] = Map(Sneza
                                                  //| na -> 3, Jana -> 3, Matija -> 3)
	mut_scores_defaulted("none")              //> res4: Int = 5
	
	// get same map with set default value as the function of all non defined keys
	val ascii_values = mutable_scores.withDefault(_.foldLeft(0)(_.toInt + _.toInt))
                                                  //> ascii_values  : scala.collection.mutable.Map[String,Int] = Map(Snezana -> 3,
                                                  //|  Jana -> 3, Matija -> 3)

	ascii_values("a")                         //> res5: Int = 97
	ascii_values("matija")                    //> res6: Int = 630


	// update operations
	mutable_scores("Matija") = 3
	mutable_scores("Lukovici") = mutable_scores("Matija") + mutable_scores.getOrElse("Jana", 0) + mutable_scores.getOrElse("Snezana", 0)
	
	mutable_scores("Lukovici")                //> res7: Int = 9
	
	// add multiple pairs at once
	mutable_scores += ("Igor" -> 2, "Sale" -> 2)
                                                  //> res8: maps.mutable_scores.type = Map(Igor -> 2, Lukovici -> 9, Sale -> 2, S
                                                  //| nezana -> 3, Jana -> 3, Matija -> 3)
	mutable_scores("Sale") + mutable_scores("Igor")
                                                  //> res9: Int = 4

	mutable_scores -= "Lukovici"              //> res10: maps.mutable_scores.type = Map(Igor -> 2, Sale -> 2, Snezana -> 3, J
                                                  //| ana -> 3, Matija -> 3)
	mutable_scores.getOrElse("Lukovici", 25)  //> res11: Int = 25

	// we can update the map with the multiple values, overwritting values for existing keys
	// matija used to have value of 2
	val new_mut_map = scores + ("Matija" -> 2.5, "Bakuta" -> 3)
                                                  //> new_mut_map  : scala.collection.Map[String,AnyVal] = Map(Matija -> 2.5, Jan
                                                  //| a -> 2.5, Snezana -> 2.5, Bakuta -> 3)

	
	// iterate
	
	for((k,v) <- mutable_scores) println(s"$k=$v")
                                                  //> Igor=2
                                                  //| Sale=2
                                                  //| Snezana=3
                                                  //| Jana=3
                                                  //| Matija=3
	for(k <- mutable_scores.keySet) println(s"$k")
                                                  //> Igor
                                                  //| Sale
                                                  //| Snezana
                                                  //| Jana
                                                  //| Matija
	// map.values for values


	// map is by default returning HashMap implementation
	// if need for keys to be in sorted manner then we need to use tree impl, sorted table map
	
	val sorted_scores = scala.collection.mutable.SortedMap(5->"Five", 3->"Three", 4->"Four")
                                                  //> sorted_scores  : scala.collection.mutable.SortedMap[Int,String] = TreeMap(3
                                                  //|  -> Three, 4 -> Four, 5 -> Five)

	// LinkedHashMap implementation exists as well, it maintains keys in insertion order
	
	// if calling java code which returns java.util.Map we would need to convert it to scala
	
	import scala.collection.JavaConversions.mapAsScalaMap
	val converted_map: scala.collection.mutable.Map[Int,String] = new java.util.TreeMap[Int,String]
                                                  //> converted_map  : scala.collection.mutable.Map[Int,String] = Map()
	// sorted map
	converted_map(5) = "Five"
	converted_map(1) = "One"
	for((k,v) <- converted_map) println(s"$k=$v")
                                                  //> 1=One
                                                  //| 5=Five

}