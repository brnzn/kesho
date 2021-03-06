package migration.entity

import org.squeryl.annotations.Column

class Student(val id: Long,
  @Column("FINANCIAL_SUPPORT") val financialSupport: Int,
  @Column("LEVEL_OF_SUPPORT") val levelOfSupport: String,
  @Column("ENRICHMENT_SUPPORT") val enrichmentSupport: Int,
  @Column("FINANCIAL_SUPPORT_STATUS") val financialSupportStatus: String,
  @Column("FINANCIAL_SUPPORT_STATUS_DETAILS") val financialSupportStatusDetails: String,
  val name: String,
  @Column("FAMILY_ID") val familyId: Long,
  @Column("GENDER") val gender: String,
  @Column("HOME_LOCATION") val homeLocation: String,
  @Column("YEAR_OF_BIRTH") val dob: Int,
  @Column("FINANCIAL_SUPPORT_STATUS_SUB_DETAILS") val financialSupportStatusSubDetails: String) {
  def this() = this(0, 0, null, 0, null, null, null, 0, null, null, 0, null)
}


    /*
//pk_student_id	
 * Receiving Financial Support?	
 * Level of Support	
 * Enrichment/Other Support	
 * Reason not receiving financial support	
 * Reason not receiving financial support_detail	
 * student_first_names	
 * student_last_name	
 * student_gender
 */