

class SymbolTable {
  import SymbolTable._
  private var table = init()

  def contains(symbol: Symbol): Boolean = table.contains(symbol)

  def apply(symbol: Symbol): Option[Address] =
    if (contains(symbol)) Some(table(symbol))
    else None

  def update(symbol: Symbol, address: Address) = table += (symbol -> address)
}

object SymbolTable {
  type Symbol = String
  type Address = String
  def apply(): SymbolTable = new SymbolTable()

  private[SymbolTable] def init(): Map[Symbol, Address] = {
    Map[Symbol, Address]("SP" -> "0000000000000000", "LCL" -> "0000000000000001", "ARG" -> "0000000000000010",
      "THIS" -> "0000000000000011", "THAT" -> "0000000000000100", "SCREEN" -> "0100000000000000", "KBD" -> "0110000000000000") ++ registersMap()
  }

  private[this] def registersMap() = {
    val keys = for {
      k <- (0 to 15)
    } yield s"R$k"
    val addresses = for {
      a <- (0 to 15)
    } yield Utils.to16bitBinaryString(a)
    keys.zip(addresses)
  }
}

