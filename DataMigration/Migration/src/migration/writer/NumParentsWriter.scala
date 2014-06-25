package migration.writer

import org.squeryl.PrimitiveTypeMode._
import migration.entity.Contact
import migration.entity.ContactDetails
import migration.Schema
import migration.entity.Family
import migration.entity.Student

class NumParentsWriter extends DataWriter {
  def insert(values: Array[String]) = {
    if(values(1) != null && values(1).trim.length() > 0) {
	    def fam = from(Schema.students)(s => where(s.id === values(0).substring(1).toLong) select (s))
	    val fId = fam.toList(0).familyId

	    update(Schema.families)(s =>
	      where(s.id === fId)
	        set (s.aliveParents := values(1).toInt))
	    }
  }
}
