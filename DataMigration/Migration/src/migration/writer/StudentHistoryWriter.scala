package migration.writer

import org.squeryl.PrimitiveTypeMode._
import migration.entity.Contact
import migration.entity.ContactDetails
import migration.Schema
import migration.entity.StudentHistory
import java.text.SimpleDateFormat
import java.util.Date

class StudentHistoryWriter extends DataWriter {
  def insert(values: Array[String]) = {
    Schema.studentHistory.insert(new StudentHistory(values(0).substring(1).toLong, parse(values(1)), values(2)))
  }

  def parse(value: String): Date = {
    val df = new SimpleDateFormat("dd/MM/yyyy");
    if (!value.trim().isEmpty()) {
      df.parse(value)
    } else {
      df.parse("01/01/1900")
    }
  }
}