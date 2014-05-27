package migration.entity

import org.squeryl.annotations.Column
import java.util.Date

class Education (
  @Column("STUDENT_ID") val studentId: Long,
  @Column("SCHOOL_ID") val schoolId: Long,
  @Column("LEVEL") val level: String,
  @Column("START_DATE") val startDate: Date,
  @Column("CLASS") val startClass: String) extends BaseEntity {
  def this() = this(0, 0, "", null, "")
}