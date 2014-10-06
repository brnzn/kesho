package migration.writer

import org.squeryl.PrimitiveTypeMode._
import migration.entity.Contact
import migration.Schema

class ContactWriter extends DataWriter {
  def insert(values: Array[String]) = {
    Schema.contacts.insert(new Contact(values(0).toLong , values(1).substring(1).toLong, values(3), values(2), values(4), values(5)))
  }
}