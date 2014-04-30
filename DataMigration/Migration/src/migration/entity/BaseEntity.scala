package migration.entity

import org.squeryl.KeyedEntity

class BaseEntity extends KeyedEntity[Long] {

  val id:Long = 0
}