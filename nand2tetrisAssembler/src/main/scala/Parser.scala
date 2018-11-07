import java.io.{BufferedReader, File, FileReader, InputStreamReader}

trait Command

case class ACommand(data: Either[String, Int]) extends Command

case class CCommand(dest: Option[String], comp: String, jump: Option[String]) extends Command

case class LCommand(symbol: String) extends Command

case class Comment() extends Command

class Parser(private val in: BufferedReader) {

  private var currentCommand: Option[Command] = None
  private var currentLine: Option[String] = readLine()

  def hasMoreCommands: Boolean = currentLine match {
    case Some(_) => true
    case _ => false
  }

  def advance = {
    currentCommand = Some(parseCommand(currentLine.getOrElse(throw new Exception())))
    currentLine = readLine()
  }

  def commandType: Command = currentCommand.getOrElse(throw new Exception())

  def symbol: String = {
    currentCommand match {
      case Some(ACommand(data)) => data match {
        case Left(l) => l
        case Right(r) => r.toString
      }
      case Some(LCommand(sym)) => sym
      case _ => throw new Exception()
    }
  }

  def dest: String = {
    currentCommand match {
      case Some(CCommand(dest, _, _)) => dest.getOrElse(throw new Exception())
      case _ => throw new Exception()
    }
  }

  def comp: String = {
    currentCommand match {
      case Some(CCommand(_, comp, _)) => comp
      case _ => throw new Exception()
    }
  }

  def jump: String = {
    currentCommand match {
      case Some(CCommand(_, _, jump)) => jump.getOrElse(throw new Exception())
      case _ => throw new Exception()
    }
  }

  private def readLine(): Option[String] = {
    val line = in.readLine()
    if (line != null) {
      val trimmedLine = line.trim
      if (trimmedLine.length == 0) readLine()
      else Some(trimmedLine)
    }
    else None
  }

  private def parseCommand(line: String): Command = {
    val chr = line.charAt(0)
    chr match {
      case '@' => parseACommand(line.substring(1).replaceAll("(//.*| )", ""))
      case '(' => LCommand(line.substring(1, line.indexOf(')')).replaceAll("(//.*| )", ""))
      case '/' => Comment()
      case _ => parseCCommand(line.replaceAll("(//.*| )", ""))
    }
  }

  private def parseACommand(str: String): ACommand = {
    val sb = new StringBuilder()
    var index = 0
    while(index < str.length) {
      if (str.charAt(index) == '/' && str.charAt(index+1) == '/') ACommand {
        val data = sb.toString
        try {Right(data.toInt)}
        catch {case _ => Left(data)}
      }
      else if (str.charAt(index) != ' ') sb.append(str.charAt(index))
      index += 1
    }
    ACommand {
      val data = sb.toString
      try {Right(data.toInt)}
      catch {case _ => Left(data)}
    }
  }

  private def parseCCommand(str: String): CCommand = {
    var split = str.split("=")
    if (split.length == 1) {
      split = str.split(";")
      new CCommand(None, split(0), Some(split(1)))
    }
    else {
      new CCommand(Some(split(0)), split(1), None)
    }
  }
}
object Parser {
  def apply(br: BufferedReader): Parser = new Parser(br)
}
