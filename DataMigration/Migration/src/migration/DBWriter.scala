package migration

import org.squeryl.PrimitiveTypeMode._
import migration.writer.DataWriter
import migration.io.LineHandler

class DBWriter(val worker: DataWriter, val line: String, val length: Int) {
  def insert() {
 //   println("==> Inserting " + line)
    val values = new LineHandler(line, length).split

    try {
      transaction {
        worker.insert(values)
        println("Inserted " + line)
      }
    } catch {
      case e: IllegalArgumentException => {
        println("skipping empty row...")
      }
      case e: Throwable => {
        println("************ Failed to insert row:" + line + "**************" + e.getMessage())
        if (e.getMessage() != null && !e.getMessage().contains("Duplicate entry")) {
          e.printStackTrace()
        }
      }
    }
  }
}

