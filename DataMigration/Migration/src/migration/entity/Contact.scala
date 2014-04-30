package migration.entity

import org.squeryl.annotations.Column

class Contact(val id: Long,
  @Column("SCHOOL_ID") val schoolId: Long,
  val name: String,
  @Column("TITLE") val title: String,
  @Column("SURNAME") val surname: String,
  @Column("JOB_TITLE") val jobtitle: String) {
  def this() = this(0, 0, "", "", "", "")
}