package migration

import org.squeryl._
import org.squeryl.PrimitiveTypeMode._

object Schema extends Schema {
val schools = table[SCHOOLS]

  on(schools)(school => declare(
     // school.id is (autoIncremented),
      school.id is (unique),
      school.name is (unique)
    ))
}