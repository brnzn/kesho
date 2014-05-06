package migration.entity

import org.squeryl.annotations.Column

class ContactDetails(
  @Column("CONTACT_ID") val contactId: Long,
  @Column("VALUE") val value: String,
  @Column("TYPE") val contactType: String,
  val comments: String) extends BaseEntity {
  def this() = this(0, "", "", "")
}