object Utils {

  def to16bitBinaryString(i: Int): String = {
    val b = i.toBinaryString
    val padding = List.fill(16-b.length)("0").mkString
    padding + b
  }

}
