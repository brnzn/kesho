package migration

import org.squeryl.PrimitiveTypeMode._
import migration.entity.Contact
import migration.writer.DataWriter

class ContactWriter extends DataWriter {
  def insert(values: Array[String]) = {
    Schema.contacts.insert(new Contact(values(0).toLong , values(1).substring(1).toLong, values(2), values(3), values(4), values(5)))
  }
}