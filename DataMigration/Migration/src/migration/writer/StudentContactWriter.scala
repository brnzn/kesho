package migration.writer

import org.squeryl.PrimitiveTypeMode._
import migration.entity.Contact
import migration.entity.ContactDetails
import migration.Schema
import migration.entity.StudentContact

class StudentContactWriter extends DataWriter {
  def insert(values: Array[String]) = {
    Schema.studentContacts.insert(new StudentContact(values(0).substring(1).toLong, values(1), "S", Option(null)))
  }
}