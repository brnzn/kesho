package migration.entity

import org.squeryl.annotations.Column

class StudentContact(
  @Column("OWNER_ID") val contactId: Long,
  @Column("VALUE") val value: String,
  @Column("TYPE") val contactType: String,
  @Column ("COMMENTS") val comments: Option[String]) extends BaseEntity {
  def this() = this(0, "", null, Option(null))
}