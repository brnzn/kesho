package migration.entity

import org.squeryl.annotations.Column

class Student(val id: Long,
  @Column("FAMILY_ID") val familyId: Long,
  val name: String,
  @Column("CURRENT_STUDENT") val currentStudent: Int,
  @Column("DEPARTURE_REASON") val departureReason: String,
  @Column("GENDER") val gender: String,
  @Column("DEPARTURE_REASON_DETAILS") val departureReasonDetails: String,
  @Column("SPONSORED") val sponsored: Int)
 {
  def this() = this(0, 0, "", 0, "", "", "", 0)
}


