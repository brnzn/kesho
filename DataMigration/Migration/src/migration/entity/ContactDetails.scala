package migration.entity

import org.squeryl.annotations.Column

class ContactDetails(
  @Column("OWNER_ID") val contactId: Long,
  @Column("VALUE") val value: String,
  @Column("TYPE") val contactType: String,
  val comments: Option[String]) extends BaseEntity {
  def this() = this(0, "", "", Option(null))
}