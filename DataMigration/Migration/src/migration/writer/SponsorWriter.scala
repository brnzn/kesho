package migration.writer

import org.squeryl.PrimitiveTypeMode._
import migration.Schema
import migration.entity.School
import migration.entity.Sponsor

class SponsorWriter extends DataWriter {
  def insert(values: Array[String]) = {
    Schema.sponsors.insert(new Sponsor(new java.lang.Long(values(0)) , values(1), values(2), values(3), values(4), values(5), values(6), values(7), values(8)))
  }
}