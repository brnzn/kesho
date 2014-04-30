package migration

import org.squeryl.annotations.Column

class SCHOOLS(val id: Long,
    val name: String,
  @Column("ADDRESS_LINE1") val addressLine1: String,
  @Column("ADDRESS_LINE2") val addressLine2: String,
  @Column("ADDRESS_LINE3") val addressLine3: String,
  @Column("COUNTY") val county: String,
  @Column("COUNTRY") val country: String,
  @Column("POSTCODE") val postcode: String = null
  ) {
  def this() = this(0, "", "", "", "", "", "", "")
}