package migration.writer

import java.text.SimpleDateFormat
import java.util.Date

import org.squeryl.PrimitiveTypeMode._

import migration.Schema
import migration.entity.Education

class EducationWriter extends DataWriter {
  def insert(values: Array[String]) = {
//    println("=> " + values.length)
    
//    val stId = EducationWriter.getStudentId(values(0));
//    println("==> " + stId);
    
    println(EducationWriter.extractSchoolId(values(4)))
    
    Schema.education.insert(new Education(new java.lang.Long(EducationWriter.getStudentId(values(0)).substring(1)) , EducationWriter.extractSchoolId(values(4)), values(3), EducationWriter.parse(values(2)), values(1)))
  }
}

object EducationWriter {
  val df = new SimpleDateFormat("dd/MM/yyyy");
  var current = ""
  
  def parse(value: String) :Date = {
    if(!value.trim().isEmpty()) {
    	df.parse(value)
    } else {
      df.parse("01/01/1900")
    }
    //null
  } 
  
  def extractSchoolId(value: String) = value match {
    case null => Option(null)
    case _ => Some(new java.lang.Long(value.substring(1)))
//    Option
//    if(value != null && !value.trim().isEmpty()) {
//      return new java.lang.Long(value.substring(1))
//    }
//    
//    return null
    
 }
  
  def getStudentId(value: String): String = {
    if(value.isEmpty()) {
      current
    } else {
      current = value
      current
    }
  }
}
/**
pk_student_id,
education_start_class,
*education_start_date,
*education_type,
*fk_school_id  
*/