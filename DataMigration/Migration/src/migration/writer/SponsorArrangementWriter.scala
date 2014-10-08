package migration.writer

import java.text.SimpleDateFormat
import java.util.Date
import org.squeryl.PrimitiveTypeMode._
import migration.Schema
import migration.entity.Education
import migration.entity.SponsorArrangement

class SponsorArrangementWriter extends DataWriter {
  def insert(values: Array[String]) = {
    Schema.payments.insert(new SponsorArrangement(values(1).substring(1).toLong,
        values(0).substring(1).toLong, 
        SponsorArrangementWriter.parse(values(3)), SponsorArrangementWriter.parse(values(4)), values(4), values(5).toFloat, values(6)))
  }
}

object SponsorArrangementWriter {
  val df = new SimpleDateFormat("dd/MM/yyyy");
  var current = ""

  def parse(value: String): Date = {
    if (!value.trim().isEmpty()) {
      df.parse(value)
    } else {
      df.parse("01/01/1900")
    }
  }
}
//student_id	fk_sponsor_id	payment_frequency	start_date	end_date	amount	currency																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																									

/**
  @Column("SPONSOR_ID") val sponsorId: Long,
  @Column("STUDENT_ID") val studentId: Long,
  @Column("START_DATE") val startDate: Date,
  @Column ("END_DATE") val comments: Date,
  @Column("TYPE") val paymentType: String,
  @Column("AMOUNT") val amount: BigDecimal,
  @Column("CURRENCY") val currency: String 
*/