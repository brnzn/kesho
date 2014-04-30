package migration

import org.squeryl.PrimitiveTypeMode._

class DBWriter(val worker: DataWriter, val line: String, val length: Int) {
  def insert() {
    println("==> Inserting " + line)
    val values = new LineHandler(line, length).split
    
    try {
	    transaction {
	      worker.insert(values)
	      println("Inserted " + line)
	    }
    } catch {
      case e: Throwable => {
        println("************ Failed to insert row:" + line + "**************")
        e.printStackTrace()
      }
    }
  }
}

