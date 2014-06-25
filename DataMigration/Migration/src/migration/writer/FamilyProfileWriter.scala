package migration.writer

import java.text.SimpleDateFormat
import java.util.Date
import org.squeryl.PrimitiveTypeMode._
import migration.Schema
import migration.entity.Education
import migration.entity.FamilyProfile

class FamilyProfileWriter extends DataWriter {
  def insert(values: Array[String]) = {
    def fam = from(Schema.students)(s => where(s.id === values(0).substring(1).toLong) select (s))
    val fId = fam.toList(0).familyId

    Schema.familyProfile.insert(new FamilyProfile(fId, FamilyProfileWriter.parse(values(1)), values(2)))
  }
}

object FamilyProfileWriter {
  val df = new SimpleDateFormat("dd/MM/yyyy");
  var current = ""

  def parse(value: String): Date = {
    if (!value.trim().isEmpty()) {
      df.parse(value.trim)
    } else {
      df.parse("01/01/1900")
    }
  }
}