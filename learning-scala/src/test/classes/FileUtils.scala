package test.classes

import scala.io.Source
import java.io.File

object FileUtils {
  
  /**
   * get all files and folders in the dir
   */
  def getChildren(dir: String): Array[File] = {
    val f = new File(dir)
    if (f.exists() && f.isDirectory()) f.listFiles() else throw new IllegalArgumentException("not a dir")
  }
  
  /**
   * get only files from dir
   */
  def getFiles(dir: String): Array[File] = {
    filesMatching(dir, _.isFile())
  }
  
  /**
   * get file lines in list
   */
  def getFileLines(file: File): List[String] = {
    if (file.isFile()) Source.fromFile(file).getLines().toList
    else throw new IllegalArgumentException("not a file")
  }
  
  /**
   * Grep all files in dir
   */
  def grepDir(dir: String, pattern: String): TraversableOnce[String] = {
    for {
      child <- getFiles(dir)
      line <- getFileLines(child)
      trimmed = line trim()
      if trimmed matches pattern
    }
    yield trimmed
  }
  
  
  private def filesMatching(dir: String, matcher: File=> Boolean): Array[File] = {
    for (file <- getChildren(dir) if matcher(file))
        yield file
  }
  
  /**
   * get files and folders from dir which names are ending in query
   */
  def filesEnding(dir: String, query: String): Array[File] = {
    filesMatching(dir, _.getName.endsWith(query))
  }
  
  /**
   * get files and folders from dir which names match query pattern
   */
  def filesRegex(dir: String, query: String): Array[File] = {
    filesMatching(dir, _.getName.matches(query))
  }
  
  
  
  def main(args: Array[String]): Unit = {
    for (
      line<-grepDir("/home/matija/Projects/show-me-the-code/learning-scala/src/test/classes",".*grep.*")
    ){println(line)}
  }
}