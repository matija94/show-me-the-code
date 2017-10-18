object tuples {


	val tupl = ("String", 1, "this" :: "is" :: "list" :: Nil, 1.5)
                                                  //> tupl  : (String, Int, List[String], Double) = (String,1,List(this, is, list),
                                                  //| 1.5)

// are 1 based
Console println	tupl._1                           //> String


Console println {tupl._3 map {e=>e length}}       //> List(4, 2, 4)

import scala.math._

Console println { pow(tupl._4,2) }                //> 2.25






}