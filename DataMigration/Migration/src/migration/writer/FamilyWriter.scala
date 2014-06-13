package migration.writer

import org.squeryl.PrimitiveTypeMode._
import migration.entity.Contact
import migration.entity.ContactDetails
import migration.Schema
import migration.entity.Family

class FamilyWriter extends DataWriter {
  def insert(values: Array[String]) = {
    if(values(0) != null && !values(0).isEmpty()) {
      
    	Schema.families.insert(new Family(values(0).trim(), 0))
    } 
  }
  
  def toInt(value: String) :Int = {
    if(value != null && value.trim().length() > 0) {
      value.toInt
    } else {
      0
    }
  }
}