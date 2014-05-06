package migration.entity

import org.squeryl.annotations.Column

class Sponsor(val id: Long,
    val name: String,
  @Column("SURNAME")  val surname: String,
  @Column("ADDRESS_LINE_1") val addressLine1: String,
  @Column("ADDRESS_LINE_2") val addressLine2: String,
  @Column("ADDRESS_LINE_3") val addressLine3: String,
  @Column("COUNTRY") val country: String,
  @Column("COUNTY") val county: String,
  @Column("POSTCODE") val postcode: String = null
  ) {
  def this() = this(0, "", "", "", "", "", "", "", "")
}