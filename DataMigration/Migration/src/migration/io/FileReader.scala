package migration.io

import java.io.File

class FileReader(fileName: String) {
  require(fileName != null)

  val file = new File(fileName)
  require(file.exists())

  def lines() =
    scala.io.Source.fromFile(file).getLines().toList

}