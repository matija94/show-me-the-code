import java.io.{BufferedReader, FileReader}

class Assembler {
  type ICode = Vector[(Command, Int)]
  type HackMachineCode = Vector[String]

  private val st = SymbolTable()
  private val codeGenerator = CodeGenerator()

  def assemble(asmFile: String): HackMachineCode = secondPass(firstPass(asmFile))

  private def firstPass(asmFile: String): ICode = {
    val parser = Parser(new BufferedReader(new FileReader(asmFile)))
    var intermediateCode = Vector.empty[(Command, Int)]
    var romCounter = 0
    while (parser.hasMoreCommands) {
      parser.advance
      val command = parser.commandType
      command match {
        case LCommand(sym) =>
          st(sym) = Utils.to16bitBinaryString(romCounter)
        case Comment() => ()
        case _ => {
          intermediateCode :+= (command, romCounter)
          romCounter += 1
        }
      }
    }
    intermediateCode foreach println
    intermediateCode
  }

  private def secondPass(iCode: ICode): HackMachineCode = {

    var nextAvailRamAddr = 16
    for {
      (command, _) <- iCode
    }
      yield {
        command match {
          case ACommand(data) => data match {
            case Right(dec) => Utils.to16bitBinaryString(dec)
            case Left(sym) => {
              if (!st.contains(sym)) {
                st(sym) = Utils.to16bitBinaryString(nextAvailRamAddr)
                nextAvailRamAddr += 1
              }
              st(sym).get
            }
          }
          case CCommand(dest, comp, jump) => {
            val abit = {
              if (comp.contains("M")) "1"
              else "0"
            }
            "111" + abit + codeGenerator.comp(comp) + codeGenerator.dest(dest.getOrElse("")) + codeGenerator.jump(jump.getOrElse(""))
          }
        }
      }
  }
}
object Assembler {
  def apply(): Assembler = new Assembler()
}

object Main extends App {

  val asm = Assembler()
  val code = asm.assemble("/Users/mlukovic/Documents/nand2tetris/projects/04/mult/Mult.asm")
  code foreach(println)
}
