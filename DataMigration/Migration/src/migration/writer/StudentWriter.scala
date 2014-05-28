package migration.writer

import org.squeryl.PrimitiveTypeMode._
import migration.entity.Contact
import migration.entity.ContactDetails
import migration.Schema
import migration.entity.Family
import migration.entity.Student

class StudentWriter extends DataWriter {
  def insert(values: Array[String]) = {
    if(values(0) != null && !values(0).isEmpty()) {
      
       def fam = from(Schema.families)(s => where(s.name === values(6).trim()) select(s))
      println("-- "+ fam.toList(0).id + "-" + fam.toList(0).name)
      	
    	Schema.students.insert(new Student(new java.lang.Long(values(0).substring(1)), fam.toList(0).id, values(5).trim(), toInt(values(2), "no"), values(3), toGender(values(7)), values(4), toInt(values(1), "no")));
    } 
    //throw new IllegalArgumentException("empty row");
  }
  
  def toInt(value: String, falseValue: String) :Int = {
    println("--> " + value)
    if(value.equalsIgnoreCase(falseValue)) {
      return 0
    } else {
      return 1
    }
  }
  
  def toGender(value: String) :String = {
    if(value != null && value.trim().length() > 0 ) {
      value.substring(0,1).toUpperCase()
    } else {
      null
    }
    
  }
}


/*
 * val id: Long,
  @Column("FAMILY_ID") val county: Long,
  val name: String,
  @Column("CURRENT_STUDENT") val currentStudent: Int,
  @Column("STUDENT_STATUS") val studentStatus: String,
  @Column("GENDER") val gender: String
 */
