package migration.writer

import org.squeryl.PrimitiveTypeMode._
import migration.entity.Contact
import migration.entity.ContactDetails
import migration.Schema

class ContactDetailsWriter extends DataWriter {
  def insert(values: Array[String]) = {
    Schema.contactDetails.insert(new ContactDetails(values(0).toLong , values(1), values(2).substring(0, 1).toUpperCase(), values(3)))
  }
}