package migration

import org.squeryl.Session
import org.squeryl.SessionFactory
import org.squeryl.PrimitiveTypeMode._
import org.squeryl.adapters.MySQLAdapter
import scala.io.Source

object Migration {
  val databaseUsername = "oren"
  val databasePassword = "oren"
  val databaseConnection = "jdbc:mysql://localhost/kesho"

  def main(args: Array[String]): Unit = {
    startDatabaseSession()

//    for (line <- Source.fromFile("./src/migration/test.txt").getLines())
//    	println(line.length +" "+ line)
 

    Source.fromFile("./src/migration/Schools-only.txt").getLines().foreach(line => new DBWriter(new SchoolWriter, line, 8).insert)  
  }

  def startDatabaseSession(): Unit = {
    Class.forName("com.mysql.jdbc.Driver")
    SessionFactory.concreteFactory = Some(() => Session.create(
      java.sql.DriverManager.getConnection(databaseConnection, databaseUsername, databasePassword),
      new MySQLAdapter))
  }
}