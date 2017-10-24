package test.classes

import scala.io.Source
import java.io.File

object FileUtils {
  
  def getChildren(dir: String): Array[File] = {
    val f = new File(dir)
    if (f.exists() && f.isDirectory()) f.listFiles() else throw new IllegalArgumentException("not a dir")
  }
  
  def getFiles(dir: String): Array[File] = {
    for{
      file <- getChildren(dir)
      if file.isFile()
    } yield file
  }
  
  def getFileLines(file: File): List[String] = {
    if (file.isFile()) Source.fromFile(file).getLines().toList
    else throw new IllegalArgumentException("not a file")
  }
  
  def grepDir(dir: String, pattern: String): TraversableOnce[String] = {
    for {
      child <- getFiles(dir)
      line <- getFileLines(child)
      trimmed = line trim()
      if trimmed matches pattern
    }
    yield trimmed
  }
  
  def main(args: Array[String]): Unit = {
    for (
      line<-grepDir("/home/matija/Projects/show-me-the-code/learning-scala/src/test/classes",".*grep.*")
    ){println(line)}
  }
}