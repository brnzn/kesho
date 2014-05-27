package migration.writer

import org.squeryl.PrimitiveTypeMode._
import migration.Schema
import migration.entity.School
import migration.entity.Education
import java.text.SimpleDateFormat

class EducationWriter extends DataWriter {
  def insert(values: Array[String]) = {
    val df = new SimpleDateFormat("dd/MM/yyyy");
    
    Schema.education.insert(new Education(new java.lang.Long(values(0).substring(1)) , new java.lang.Long(values(4).substring(1)), values(3), df.parse(values(2)), values(1)))
  }
}
