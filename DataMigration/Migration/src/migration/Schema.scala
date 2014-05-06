package migration

import org.squeryl._
import org.squeryl.PrimitiveTypeMode._
import migration.entity.Contact
import migration.entity.School
import migration.entity.ContactDetails
import migration.entity.Sponsor
import migration.entity.SponsorContact

object Schema extends Schema {
  val schools = table[School]("SCHOOLS")
  
  val contacts = table[Contact]("CONTACT")
  val contactDetails = table[ContactDetails]("CONTACT_DETAIL")
  val sponsors = table[Sponsor]("SPONSORS")
  
  val sponsorContacts = table[SponsorContact]("SPONSOR_CONTACT")

  on(sponsors)(sponsor => declare(
    sponsor.id is (unique)))

    on(sponsorContacts)(detail => declare(
    detail.id is (autoIncremented)))

  on(schools)(school => declare(
    school.id is (unique),
    school.name is (unique)))

  on(contacts)(contact => declare(
    contact.id is (unique)))

  on(contactDetails)(detail => declare(
    detail.id is (autoIncremented)))
}