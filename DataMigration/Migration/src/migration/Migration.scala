package migration

import scala.io.Source
import org.squeryl.Session
import org.squeryl.SessionFactory
import org.squeryl.adapters.MySQLAdapter
import migration.writer.SchoolWriter
import migration.writer.ContactWriter
import migration.writer.ContactDetailsWriter
import migration.writer.SponsorWriter
import migration.writer.FamilyWriter
import migration.writer.StudentWriter
import migration.writer.EducationWriter
import migration.writer.StudentHistoryWriter

object Migration {
  val databaseUsername = "oren"
  val databasePassword = "oren"
  val databaseConnection = "jdbc:mysql://localhost/kesho"

  def main(args: Array[String]): Unit = {
    startDatabaseSession()

//    for (line <- Source.fromFile("./src/migration/test.txt").getLines())
//    	println(line.length +" "+ line)
 

//    Source.fromFile("./src/data/Schools-only-final.txt").getLines().foreach(line => new DBWriter(new SchoolWriter, line, 8).insert)  
//    Source.fromFile("./src/data/Schools-contacts-only-final.txt").getLines().foreach(line => new DBWriter(new ContactWriter, line, 6).insert)  
//    Source.fromFile("./src/data/Schools-contacts-details-final.txt").getLines().foreach(line => new DBWriter(new ContactDetailsWriter, line, 5).insert)  

    Source.fromFile("./src/data/sponsors.txt").getLines().foreach(line => new DBWriter(new SponsorWriter, line, 11).insert)  
//    Source.fromFile("./src/data/SponsorsContactsDetails-only.txt").getLines().foreach(line => new DBWriter(new SponsorContactWriter, line, 4).insert)  

    //Source.fromFile("./src/data/Families.txt").getLines().foreach(line => new DBWriter(new FamilyWriter, line, 2).insert)  
    //Source.fromFile("./src/data/students.txt").getLines().foreach(line => new DBWriter(new StudentWriter, line, 9).insert)
    //Source.fromFile("./src/data/education.txt").getLines().foreach(line => new DBWriter(new EducationWriter, line, 5).insert)
    //Source.fromFile("./src/data/studentHistory.txt").getLines().foreach(line => new DBWriter(new StudentHistoryWriter, line, 3).insert)

  }

  def startDatabaseSession(): Unit = {
    Class.forName("com.mysql.jdbc.Driver")
    SessionFactory.concreteFactory = Some(() => Session.create(
      java.sql.DriverManager.getConnection(databaseConnection, databaseUsername, databasePassword),
      new MySQLAdapter))
  }
}