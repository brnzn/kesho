package migration.entity

import org.squeryl.annotations.Column

class Sponsor(val id: Long,
    val name: String,
  @Column("SURNAME")  val surname: String,
  val email1: String,
  val email2: String,
  @Column("ADDRESS_LINE_1") val addressLine1: String,
  @Column("ADDRESS_LINE_2") val addressLine2: String,
  @Column("ADDRESS_LINE_3") val addressLine3: String,
  @Column("COUNTRY") val country: String,
  @Column("COUNTY") val county: String,
  @Column("POSTCODE") val postcode: String = null
  ) {
  def this() = this(0, "", "", "", "", "", "", "", "", "", "")
}

//id sponsor_first_name	sponsor_last_name	email1	sponsor_email	sponsor_address_1	sponsor_address_2	sponsor_address_3	sponsor_address_country	sponsor_address_county	sponsor_address_postcode
