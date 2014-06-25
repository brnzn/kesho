package migration.entity

import org.squeryl.annotations.Column

class Family(
  @Column("FAMILY_NAME") val name: String,
  @Column("ALIVE_PARENTS") val aliveParents: Int
  ) extends BaseEntity {
  def this() = this("",-1)
}