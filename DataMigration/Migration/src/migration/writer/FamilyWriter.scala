package migration.writer

import org.squeryl.PrimitiveTypeMode._
import migration.entity.Contact
import migration.entity.ContactDetails
import migration.Schema
import migration.entity.Family

class FamilyWriter extends DataWriter {
  def insert(values: Array[String]) = {
    if(values(0) != null && !values(0).isEmpty()) {
      
//       def fam = from(Schema.families)(s => where(s.name === values(0).trim()) select(s))
 //     	println("-- "+ fam.toList(0).id + "-" + fam.toList(0).name)
      	
    	Schema.families.insert(new Family(values(0).trim(), toInt(values(1))))
    } 
//    else {
//      throw new IllegalArgumentException("empty row");
//    }
  }
  
  def toInt(value: String) :Int = {
    if(value != null && value.trim().length() > 0) {
      value.toInt
    } else {
      0
    }
  }
}