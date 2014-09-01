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

    Schema.education.insert(new Education(new java.lang.Long(EducationWriter.getStudentId(values(0)).substring(1)), EducationWriter.extractSchoolId(values(4)), getLevel(values(3)), EducationWriter.parse(values(2)), EducationWriter.extractClass(values(1)), getsecLevel1(values(3)), getsecLevel2(values(3))))
  }

  def getLevel(value: String): String = {
    var level = value.split(" ")(0);
    if ("Tertiary".equals(level)) {
      "GapAfterTertiary"
    } else {
      level
    }
  }

  //Tertiary GapAfterTertiary
  //Primary level1 = Boarding or Day
  //Secondary level1 = District level2 = Boarding or Day 
  def getsecLevel1(value: String): String = {
    val levels = value.split(" ")
    if (levels.length > 1) {
      if ("Primary".equals(levels(0))) {
        return levels(1)
      } else if ("Secondary".equals(levels(0))) {
        return "District"
      }
    }
    null
  }

  def getsecLevel2(value: String): String = {
    val levels = value.split(" ")
    if (levels.length > 1) {
      if ("Secondary".equals(levels(0))) {
        return levels(1)
      }
    }
    null
  }
}

object EducationWriter {
  val df = new SimpleDateFormat("dd/MM/yyyy");
  var current = ""

  def parse(value: String): Date = {
    if (!value.trim().isEmpty()) {
      df.parse(value)
    } else {
      df.parse("01/01/1900")
    }
  }

  def extractClass(value: String) = value match {
    case null => Option(null)
    case "" => Option(null)
    case _ => Some(value)
  }

  def extractSchoolId(value: String) = value match {
    case null => Option(null)
    case _ => Some(new java.lang.Long(value.substring(1)))
  }

  def getStudentId(value: String): String = {
    if (value.isEmpty()) {
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