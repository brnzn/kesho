package migration.io

import scala.Array.canBuildFrom

class LineHandler (line: String, length: Int = -1) {
  require(line != null)
  
  def split() : Array[String] = {
    val arr = line.split(",")
    
    if(length > 0) {
    	arr.padTo(length, null)
    } else {
      arr
    }
  }
}