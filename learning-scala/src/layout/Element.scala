package layout

import layout.Element.ArrayElement
import Element.elem
abstract class Element {

  def contents: Array[String]

  def height: Int = contents.length

  def width: Int = if (height == 0) 0 else contents(0).length

  def widen(w: Int): Element = {
    if (width >= w) this
    else {
      val left = elem(' ', (w-width)/2, height)
      val right = elem(' ', w-width-left.width, height)
      left beside this beside right
    }
  }
  
  def heighten(h: Int): Element = {
    if (height >= h) this
    else {
      val top = elem(' ', width, (h-height)/2)
      val bot = elem(' ', width, h-height-top.height)
      top above this above bot
    }
  }

  def above(that: Element): Element = {
     val this1 = this widen that.width
     val that1 = that widen this.width
     elem(this1.contents ++ that1.contents)
  }

  def beside(that: Element): Element = {
    val this1 = this heighten that.height
    val that1 = that heighten this.height
    elem(
        for ((line1, line2) <- this1.contents zip that1.contents) yield line1 + line2
    )
  }
  override def toString = contents mkString "\n"

}
object Element {

  private class ArrayElement(conts: Array[String]) extends Element {
    if (conts.isEmpty) throw new IllegalArgumentException()
    else {
      var valid = true
      val size = conts(0).length; var i = 1
      while (valid && i < conts.length) {
        if (size != conts(i).length) valid = false
        i += 1
      }
      if (!valid) throw new IllegalArgumentException()
    }

    def contents: Array[String] = conts

  }

  private class LineElement(s: String) extends Element {

    val contents = Array(s)
    override def height = 1
    override def width = s.length
  }

  private class UniformElement(
    ch: Char,
    override val width: Int,
    override val height: Int) extends Element {

    private val line = ch.toString * width

    def contents = Array.fill(height)(line)
  }

  def elem(arr: Array[String]): Element = new ArrayElement(arr)

  def elem(s: String): Element = new LineElement(s)

  def elem(ch: Char, width: Int, height: Int): Element = new UniformElement(ch, width, height)

  def main(args: Array[String]): Unit = {
    val s = elem(Array("Matija", "Lukovi"))
    val sm = s above elem(Array("tes"))
    println(sm)
  }
}

