package migration.writer

import org.squeryl.PrimitiveTypeMode._
import migration.Schema
import migration.entity.School

class SchoolWriter extends DataWriter {
  def insert(values: Array[String]) = {
    Schema.schools.insert(new School(new java.lang.Long(values(0).substring(1)) , values(1), values(2), values(3), values(4), values(5), values(6), values(7)))
  }
}