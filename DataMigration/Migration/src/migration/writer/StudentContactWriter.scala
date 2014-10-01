package migration.writer

import migration.Schema
import migration.entity.ContactDetails

class StudentContactWriter extends DataWriter {
  def insert(values: Array[String]) = {
    Schema.contactDetails.insert(new ContactDetails(values(0).substring(1).toLong, values(1), "P", Option(null)))
  }
}