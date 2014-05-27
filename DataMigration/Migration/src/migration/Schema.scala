package migration

import org.squeryl._
import org.squeryl.PrimitiveTypeMode._
import migration.entity.Contact
import migration.entity.ContactDetails
import migration.entity.Sponsor
import migration.entity.SponsorContact
import migration.entity.Family
import migration.entity.Student
import migration.entity.School
import migration.entity.Education

object Schema extends Schema {
  val schools = table[School]("SCHOOLS")
  val families = table[Family]("FAMILY")
  val students = table[Student]("STUDENTS")
  val education = table[Education]("EDUCATION_HISTORY")

  val contacts = table[Contact]("CONTACT")
  val contactDetails = table[ContactDetails]("CONTACT_DETAIL")
  val sponsors = table[Sponsor]("SPONSORS")

  val sponsorContacts = table[SponsorContact]("SPONSOR_CONTACT")

  on(education)(detail => declare(
    detail.id is (autoIncremented)))

  on(students)(student => declare(
    student.id is (unique)))

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

  on(families)(family => declare(
    family.name is (unique),
    family.id is (autoIncremented)))
}