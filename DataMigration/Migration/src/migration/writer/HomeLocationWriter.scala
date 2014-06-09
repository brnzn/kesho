package migration.writer

import org.squeryl.PrimitiveTypeMode._
import migration.entity.Contact
import migration.entity.ContactDetails
import migration.Schema
import migration.entity.Family
import migration.entity.Student

class HomeLocationWriter extends DataWriter {
  def insert(values: Array[String]) = {
    if(values(0) != null && !values(0).isEmpty()) {
       update(Schema.students)(s =>
         	where(s.id === values(0).trim().substring(1).toLong)
         	set(s.homeLocation := values(1))
         )    	
    } 
    
  }
}
