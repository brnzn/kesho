package migration.writer

import org.squeryl.PrimitiveTypeMode._
import migration.entity.Contact
import migration.entity.ContactDetails
import migration.Schema
import migration.entity.StudentContact

class FamilyContactWriter extends DataWriter {
  def insert(values: Array[String]) = {
    
    def fam = from(Schema.students)(s => where(s.id === values(0).substring(1).toLong) select(s))
    val fId = fam.toList(0).familyId

    Schema.studentContacts.insert(new StudentContact(fId, values(1), "F", comments(values(3))))
    
    if(!values(2).trim().isEmpty()) {
    	Schema.studentContacts.insert(new StudentContact(fId, values(2), "F", comments(values(4))))
    }
  }

  def comments(value: String) = value match {
    case null => Option(null)
    case _ => Some(value)
  }
}