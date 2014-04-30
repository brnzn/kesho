package migration

import org.squeryl._
import org.squeryl.PrimitiveTypeMode._
import migration.entity.Contact
import migration.entity.School

object Schema extends Schema {
  val schools = table[School]("SCHOOLS")
  val contacts = table[Contact]("CONTACT")

  on(schools)(school => declare(
    // school.id is (autoIncremented),
    school.id is (unique),
    school.name is (unique)))

  on(contacts)(contact => declare(
    contact.id is (unique)))
}