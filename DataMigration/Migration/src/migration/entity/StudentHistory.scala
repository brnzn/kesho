package migration.entity

import org.squeryl.annotations.Column
import java.util.Date

class StudentHistory(
  @Column("STUDENT_ID") val studentId: Long,
  val date: Date,
  val details: String) extends BaseEntity {
  def this() = this(0, null, "")
}