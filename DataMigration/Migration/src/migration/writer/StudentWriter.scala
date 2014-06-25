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
       def fam = from(Schema.families)(s => where(s.name === values(7).trim()) select(s))
       //println(fam.size)
      //println("-- "+ fam.toList(0).id + "-" + fam.toList(0).name)
       //fam.toList(0).id, , toInt(values(2), "no"), , , values(4), 
    	Schema.students.insert(new Student(new java.lang.Long(values(0).substring(1)), toInt(values(1), "no"), null, toInt(values(3), "no"), toDepartureReason(values(4)),toDepartureReasonDetails(values(5)), values(6).trim, fam.toList(0).id, toGender(values(8)), null, 0));
    } 
    
    /*
//pk_student_id	
 * Receiving Financial Support?	
 * Level of Support	
 * Enrichment/Other Support	
 * Reason not receiving financial support	
 * Reason not receiving financial support_detail	
 * student_first_names	
 * student_last_name	
 * student_gender
 */
    //throw new IllegalArgumentException("empty row");
  }
  
  def toInt(value: String, falseValue: String) :Int = {
    //println("--> " + value)
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
  
  //TODFO: use pattern match
  def toDepartureReason(value: String) :String = {
    if(value.trim().equalsIgnoreCase("Graduated more than 2 years ago")) {
      "GRADUATED"
    } else if (value.trim().equalsIgnoreCase("Expiry of One Off Donation")) {
      "DONATION_EXPIRY"
    } else if (value.trim().equalsIgnoreCase("Sponsorship Withdrawn")) {
      "WITHDRAWN"
    } else if (value.trim().equalsIgnoreCase("Gap Year")) {
      "GAP_YEAR"
    } else if(value.trim.equalsIgnoreCase("Other")) {
      "OTHER"
    } else if (value.trim().length() > 0) {
      throw new IllegalArgumentException(value + " not supported");
    } else {
      null
    }
  }
  
  def toDepartureReasonDetails(value: String) :String = {
    if(value.trim().equalsIgnoreCase("Post Primary")) {
      "POST_PRIMARY"
    } else if (value.trim().equalsIgnoreCase("Post Secondary")) {
      "POST_SECONDARY"
    } else if (value.trim().equalsIgnoreCase("Post College")) {
      "POST_COLLEGE"
    } else if (value.trim().equalsIgnoreCase("Post University")) {
      "POST_UNI"
    } else if (value.trim().equalsIgnoreCase("Nursery")) {
      "NURSERY"
    } else if (value.trim().equalsIgnoreCase("Primary")) {
      "PRIMARY"
    } else if (value.trim().equalsIgnoreCase("Secondary")) {
      "SECONDARY"
    } else if (value.trim().equalsIgnoreCase("College")) {
      "COLLEGE"
    } else if (value.trim().equalsIgnoreCase("University")) {
      "UNI"
    } else if (value.trim().equalsIgnoreCase("Poor Cooperation")) {
      "POOR_COOPERATION"
    } else if (value.trim().equalsIgnoreCase("Poor Performance")) {
      "POOR_PERFORMANCE"
    } else if (value.trim().equalsIgnoreCase("Dropped Out")) {
      "DROPPED_OUT"
    } else if (value.trim().equalsIgnoreCase("Family found another sponsor")) {
      "GOT_ANOTHER_SPONSOR"
    } else if (value.trim().equalsIgnoreCase("Referred by Kesho to new sponsor")) {
      "REFERRED_TO_ANOTHER_SPONSOR"
    } else if (value.trim().equalsIgnoreCase("Family able to pay")) {
      "ABLE_TO_PAY"
    } else if (value.trim().equalsIgnoreCase("Pregnant / Married")) {
      "PREGNANT"
    } else if (value.trim().equalsIgnoreCase("Dishonesty")) {
      "DISHONESTY"
    } else if (value.trim().equalsIgnoreCase("Parents Decision")) {
      "PARENTS_DECISION"
    } else if (value.trim().length() > 0) {
      value.trim
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
