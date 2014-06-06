package migration.writer

import org.squeryl.PrimitiveTypeMode._
import migration.Schema
import migration.entity.School
import migration.entity.Sponsor

class SponsorWriter extends DataWriter {
  def insert(values: Array[String]) = {
    Schema.sponsors.insert(new Sponsor(new java.lang.Long(values(0).substring(1)) , values(1), values(2), values(3), values(4), values(5), values(6), values(7), values(8), values(9), values(10)))
  }
}

////id sponsor_first_name	sponsor_last_name	email1	sponsor_email	sponsor_address_1	sponsor_address_2	sponsor_address_3	sponsor_address_country	sponsor_address_county	sponsor_address_postcode