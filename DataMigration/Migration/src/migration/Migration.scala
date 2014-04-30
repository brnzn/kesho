package migration

import scala.io.Source
import org.squeryl.Session
import org.squeryl.SessionFactory
import org.squeryl.adapters.MySQLAdapter
import migration.writer.SchoolWriter

object Migration {
  val databaseUsername = "oren"
  val databasePassword = "oren"
  val databaseConnection = "jdbc:mysql://localhost/kesho"

  def main(args: Array[String]): Unit = {
    startDatabaseSession()

//    for (line <- Source.fromFile("./src/migration/test.txt").getLines())
//    	println(line.length +" "+ line)
 

    Source.fromFile("./src/data/Schools-only.txt").getLines().foreach(line => new DBWriter(new SchoolWriter, line, 8).insert)  
    Source.fromFile("./src/data/contacts-only.txt").getLines().foreach(line => new DBWriter(new ContactWriter, line, 6).insert)  
  }

  def startDatabaseSession(): Unit = {
    Class.forName("com.mysql.jdbc.Driver")
    SessionFactory.concreteFactory = Some(() => Session.create(
      java.sql.DriverManager.getConnection(databaseConnection, databaseUsername, databasePassword),
      new MySQLAdapter))
  }
}