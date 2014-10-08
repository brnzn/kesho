package migration.entity

import org.squeryl.annotations.Column
import java.util.Date

class SponsorArrangement(
  @Column("SPONSOR_ID") val sponsorId: Long,
  @Column("STUDENT_ID") val studentId: Long,
  @Column("START_DATE") val startDate: Date,
  @Column ("END_DATE") val endDate: Date,
  @Column("TYPE") val paymentType: String,
  @Column("AMOUNT") val amount: BigDecimal,
  @Column("CURRENCY") val currency: String
  ) extends BaseEntity {
  def this() = this(0, 0, null, null, "", null, "")
}