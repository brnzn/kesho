package migration

import scala.io.Source
import org.squeryl.Session
import org.squeryl.SessionFactory
import org.squeryl.adapters.MySQLAdapter
import migration.writer.SchoolWriter
import migration.writer.ContactWriter
import migration.writer.ContactDetailsWriter
import migration.writer.SponsorWriter
import migration.writer.SponsorContactWriter

object Migration {
  val databaseUsername = "oren"
  val databasePassword = "oren"
  val databaseConnection = "jdbc:mysql://localhost/kesho"

  def main(args: Array[String]): Unit = {
    startDatabaseSession()

//    for (line <- Source.fromFile("./src/migration/test.txt").getLines())
//    	println(line.length +" "+ line)
 

//    Source.fromFile("./src/data/Schools-only-final.txt").getLines().foreach(line => new DBWriter(new SchoolWriter, line, 8).insert)  
    Source.fromFile("./src/data/Schools-contacts-only-final.txt").getLines().foreach(line => new DBWriter(new ContactWriter, line, 6).insert)  
//    Source.fromFile("./src/data/contact-detail-only.txt").getLines().foreach(line => new DBWriter(new ContactDetailsWriter, line, 5).insert)  

//    Source.fromFile("./src/data/Sponsors-only.txt").getLines().foreach(line => new DBWriter(new SponsorWriter, line, 9).insert)  
//    Source.fromFile("./src/data/SponsorsContactsDetails-only.txt").getLines().foreach(line => new DBWriter(new SponsorContactWriter, line, 4).insert)  

  }

  def startDatabaseSession(): Unit = {
    Class.forName("com.mysql.jdbc.Driver")
    SessionFactory.concreteFactory = Some(() => Session.create(
      java.sql.DriverManager.getConnection(databaseConnection, databaseUsername, databasePassword),
      new MySQLAdapter))
  }
}