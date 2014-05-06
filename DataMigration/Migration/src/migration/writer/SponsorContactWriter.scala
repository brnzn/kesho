package migration.writer

import org.squeryl.PrimitiveTypeMode._
import migration.entity.Contact
import migration.entity.ContactDetails
import migration.Schema
import migration.entity.SponsorContact

class SponsorContactWriter extends DataWriter {
  def insert(values: Array[String]) = {
    Schema.sponsorContacts.insert(new SponsorContact(values(0).toLong , values(1), values(2).substring(0, 1).toUpperCase(), values(3)))
  }
}