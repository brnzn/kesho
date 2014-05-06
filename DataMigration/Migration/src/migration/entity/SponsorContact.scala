package migration.entity

import org.squeryl.annotations.Column

class SponsorContact(
  @Column("SPONSOR_ID") val sponsorId: Long,
  @Column("VALUE") val value: String,
  @Column("TYPE") val contactType: String,
  val comments: String) extends BaseEntity {
  def this() = this(0, "", "", "")
}