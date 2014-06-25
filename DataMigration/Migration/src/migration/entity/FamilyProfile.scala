package migration.entity

import org.squeryl.annotations.Column
import java.util.Date

class FamilyProfile (
  @Column("FAMILY_ID") val familyId: Long,
  @Column("DATE") val date: Date,
  @Column("COMMENTS") val comments: String) extends BaseEntity {
  def this() = this(0, null, null)
}