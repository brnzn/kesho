package migration

import org.squeryl.Session
import org.squeryl.SessionFactory
import org.squeryl.PrimitiveTypeMode._
import org.squeryl.adapters.MySQLAdapter

object TestApp { //extends App {
  val databaseUsername = "oren"
  val databasePassword = "oren"
  val databaseConnection = "jdbc:mysql://localhost/kesho"

  def main(args:Array[String]):Unit = {

    startDatabaseSession()

    transaction {
      val school:SCHOOLS = new SCHOOLS(1,"name", "addr1", "addr2", "addr3", "county", "country", "postcode")
      Schema.schools.insert(school)
      println("Inserted user1")      
    }

  }

  def startDatabaseSession():Unit = {
    Class.forName("com.mysql.jdbc.Driver")
      SessionFactory.concreteFactory = Some(() => Session.create(
          java.sql.DriverManager.getConnection(databaseConnection, databaseUsername, databasePassword),
          new MySQLAdapter)
        )
  }


}