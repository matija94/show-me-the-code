package layout

import Element.elem

object Spiral {
  
  val space = elem(" ")
  val corner = elem("+")  

  def spiral(nEdges: Int, direction: Int): Element = {
    if (nEdges == 1)
      elem("+")
    else {
      elem("")
    }
  }
}