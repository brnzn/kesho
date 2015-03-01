/**
 * This class is generated by jOOQ
 */
package com.kesho.generated;

/**
 * A class modelling foreign key relationships between tables of the <code>kesho</code> 
 * schema
 */
@javax.annotation.Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.5.3"
	},
	comments = "This class is generated by jOOQ"
)
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

	// -------------------------------------------------------------------------
	// IDENTITY definitions
	// -------------------------------------------------------------------------

	public static final org.jooq.Identity<com.kesho.generated.tables.records.ContactRecord, java.lang.Long> IDENTITY_CONTACT = Identities0.IDENTITY_CONTACT;
	public static final org.jooq.Identity<com.kesho.generated.tables.records.ContactDetailRecord, java.lang.Long> IDENTITY_CONTACT_DETAIL = Identities0.IDENTITY_CONTACT_DETAIL;
	public static final org.jooq.Identity<com.kesho.generated.tables.records.EducationHistoryRecord, java.lang.Long> IDENTITY_EDUCATION_HISTORY = Identities0.IDENTITY_EDUCATION_HISTORY;
	public static final org.jooq.Identity<com.kesho.generated.tables.records.FamilyRecord, java.lang.Long> IDENTITY_FAMILY = Identities0.IDENTITY_FAMILY;
	public static final org.jooq.Identity<com.kesho.generated.tables.records.FamilyProfileRecord, java.lang.Long> IDENTITY_FAMILY_PROFILE = Identities0.IDENTITY_FAMILY_PROFILE;
	public static final org.jooq.Identity<com.kesho.generated.tables.records.PaymentArrangementsRecord, java.lang.Long> IDENTITY_PAYMENT_ARRANGEMENTS = Identities0.IDENTITY_PAYMENT_ARRANGEMENTS;
	public static final org.jooq.Identity<com.kesho.generated.tables.records.SchoolsRecord, java.lang.Long> IDENTITY_SCHOOLS = Identities0.IDENTITY_SCHOOLS;
	public static final org.jooq.Identity<com.kesho.generated.tables.records.SponsorsRecord, java.lang.Long> IDENTITY_SPONSORS = Identities0.IDENTITY_SPONSORS;
	public static final org.jooq.Identity<com.kesho.generated.tables.records.StudentsRecord, java.lang.Long> IDENTITY_STUDENTS = Identities0.IDENTITY_STUDENTS;
	public static final org.jooq.Identity<com.kesho.generated.tables.records.StudentHistoryRecord, java.lang.Long> IDENTITY_STUDENT_HISTORY = Identities0.IDENTITY_STUDENT_HISTORY;

	// -------------------------------------------------------------------------
	// UNIQUE and PRIMARY KEY definitions
	// -------------------------------------------------------------------------

	public static final org.jooq.UniqueKey<com.kesho.generated.tables.records.ContactRecord> KEY_CONTACT_PRIMARY = UniqueKeys0.KEY_CONTACT_PRIMARY;
	public static final org.jooq.UniqueKey<com.kesho.generated.tables.records.ContactDetailRecord> KEY_CONTACT_DETAIL_PRIMARY = UniqueKeys0.KEY_CONTACT_DETAIL_PRIMARY;
	public static final org.jooq.UniqueKey<com.kesho.generated.tables.records.EducationHistoryRecord> KEY_EDUCATION_HISTORY_PRIMARY = UniqueKeys0.KEY_EDUCATION_HISTORY_PRIMARY;
	public static final org.jooq.UniqueKey<com.kesho.generated.tables.records.FamilyRecord> KEY_FAMILY_PRIMARY = UniqueKeys0.KEY_FAMILY_PRIMARY;
	public static final org.jooq.UniqueKey<com.kesho.generated.tables.records.FamilyRecord> KEY_FAMILY_FAMILY_NAME_UNIQUE = UniqueKeys0.KEY_FAMILY_FAMILY_NAME_UNIQUE;
	public static final org.jooq.UniqueKey<com.kesho.generated.tables.records.FamilyProfileRecord> KEY_FAMILY_PROFILE_PRIMARY = UniqueKeys0.KEY_FAMILY_PROFILE_PRIMARY;
	public static final org.jooq.UniqueKey<com.kesho.generated.tables.records.PaymentArrangementsRecord> KEY_PAYMENT_ARRANGEMENTS_PRIMARY = UniqueKeys0.KEY_PAYMENT_ARRANGEMENTS_PRIMARY;
	public static final org.jooq.UniqueKey<com.kesho.generated.tables.records.SchoolsRecord> KEY_SCHOOLS_PRIMARY = UniqueKeys0.KEY_SCHOOLS_PRIMARY;
	public static final org.jooq.UniqueKey<com.kesho.generated.tables.records.SchoolsRecord> KEY_SCHOOLS_UNIQUE_SCHOOL_NAME = UniqueKeys0.KEY_SCHOOLS_UNIQUE_SCHOOL_NAME;
	public static final org.jooq.UniqueKey<com.kesho.generated.tables.records.SponsorsRecord> KEY_SPONSORS_PRIMARY = UniqueKeys0.KEY_SPONSORS_PRIMARY;
	public static final org.jooq.UniqueKey<com.kesho.generated.tables.records.StudentsRecord> KEY_STUDENTS_PRIMARY = UniqueKeys0.KEY_STUDENTS_PRIMARY;
	public static final org.jooq.UniqueKey<com.kesho.generated.tables.records.StudentHistoryRecord> KEY_STUDENT_HISTORY_PRIMARY = UniqueKeys0.KEY_STUDENT_HISTORY_PRIMARY;

	// -------------------------------------------------------------------------
	// FOREIGN KEY definitions
	// -------------------------------------------------------------------------

	public static final org.jooq.ForeignKey<com.kesho.generated.tables.records.ContactRecord, com.kesho.generated.tables.records.SchoolsRecord> CONTACT_IBFK_1 = ForeignKeys0.CONTACT_IBFK_1;
	public static final org.jooq.ForeignKey<com.kesho.generated.tables.records.EducationHistoryRecord, com.kesho.generated.tables.records.StudentsRecord> EDUCATION_HISTORY_IBFK_1 = ForeignKeys0.EDUCATION_HISTORY_IBFK_1;
	public static final org.jooq.ForeignKey<com.kesho.generated.tables.records.EducationHistoryRecord, com.kesho.generated.tables.records.SchoolsRecord> EDUCATION_HISTORY_IBFK_2 = ForeignKeys0.EDUCATION_HISTORY_IBFK_2;
	public static final org.jooq.ForeignKey<com.kesho.generated.tables.records.FamilyProfileRecord, com.kesho.generated.tables.records.FamilyRecord> FK_FAMILY_PROFILE_FAMILY = ForeignKeys0.FK_FAMILY_PROFILE_FAMILY;
	public static final org.jooq.ForeignKey<com.kesho.generated.tables.records.PaymentArrangementsRecord, com.kesho.generated.tables.records.SponsorsRecord> PAYMENT_ARRANGEMENTS_IBFK_1 = ForeignKeys0.PAYMENT_ARRANGEMENTS_IBFK_1;
	public static final org.jooq.ForeignKey<com.kesho.generated.tables.records.PaymentArrangementsRecord, com.kesho.generated.tables.records.StudentsRecord> PAYMENT_ARRANGEMENTS_IBFK_2 = ForeignKeys0.PAYMENT_ARRANGEMENTS_IBFK_2;
	public static final org.jooq.ForeignKey<com.kesho.generated.tables.records.StudentsRecord, com.kesho.generated.tables.records.FamilyRecord> FAMILY_STUDENT_FK = ForeignKeys0.FAMILY_STUDENT_FK;
	public static final org.jooq.ForeignKey<com.kesho.generated.tables.records.StudentHistoryRecord, com.kesho.generated.tables.records.StudentsRecord> FK_STUDENT_HISTORY_STUDENT = ForeignKeys0.FK_STUDENT_HISTORY_STUDENT;

	// -------------------------------------------------------------------------
	// [#1459] distribute members to avoid static initialisers > 64kb
	// -------------------------------------------------------------------------

	private static class Identities0 extends org.jooq.impl.AbstractKeys {
		public static org.jooq.Identity<com.kesho.generated.tables.records.ContactRecord, java.lang.Long> IDENTITY_CONTACT = createIdentity(com.kesho.generated.tables.Contact.CONTACT, com.kesho.generated.tables.Contact.CONTACT.ID);
		public static org.jooq.Identity<com.kesho.generated.tables.records.ContactDetailRecord, java.lang.Long> IDENTITY_CONTACT_DETAIL = createIdentity(com.kesho.generated.tables.ContactDetail.CONTACT_DETAIL, com.kesho.generated.tables.ContactDetail.CONTACT_DETAIL.ID);
		public static org.jooq.Identity<com.kesho.generated.tables.records.EducationHistoryRecord, java.lang.Long> IDENTITY_EDUCATION_HISTORY = createIdentity(com.kesho.generated.tables.EducationHistory.EDUCATION_HISTORY, com.kesho.generated.tables.EducationHistory.EDUCATION_HISTORY.ID);
		public static org.jooq.Identity<com.kesho.generated.tables.records.FamilyRecord, java.lang.Long> IDENTITY_FAMILY = createIdentity(com.kesho.generated.tables.Family.FAMILY, com.kesho.generated.tables.Family.FAMILY.ID);
		public static org.jooq.Identity<com.kesho.generated.tables.records.FamilyProfileRecord, java.lang.Long> IDENTITY_FAMILY_PROFILE = createIdentity(com.kesho.generated.tables.FamilyProfile.FAMILY_PROFILE, com.kesho.generated.tables.FamilyProfile.FAMILY_PROFILE.ID);
		public static org.jooq.Identity<com.kesho.generated.tables.records.PaymentArrangementsRecord, java.lang.Long> IDENTITY_PAYMENT_ARRANGEMENTS = createIdentity(com.kesho.generated.tables.PaymentArrangements.PAYMENT_ARRANGEMENTS, com.kesho.generated.tables.PaymentArrangements.PAYMENT_ARRANGEMENTS.ID);
		public static org.jooq.Identity<com.kesho.generated.tables.records.SchoolsRecord, java.lang.Long> IDENTITY_SCHOOLS = createIdentity(com.kesho.generated.tables.Schools.SCHOOLS, com.kesho.generated.tables.Schools.SCHOOLS.ID);
		public static org.jooq.Identity<com.kesho.generated.tables.records.SponsorsRecord, java.lang.Long> IDENTITY_SPONSORS = createIdentity(com.kesho.generated.tables.Sponsors.SPONSORS, com.kesho.generated.tables.Sponsors.SPONSORS.ID);
		public static org.jooq.Identity<com.kesho.generated.tables.records.StudentsRecord, java.lang.Long> IDENTITY_STUDENTS = createIdentity(com.kesho.generated.tables.Students.STUDENTS, com.kesho.generated.tables.Students.STUDENTS.ID);
		public static org.jooq.Identity<com.kesho.generated.tables.records.StudentHistoryRecord, java.lang.Long> IDENTITY_STUDENT_HISTORY = createIdentity(com.kesho.generated.tables.StudentHistory.STUDENT_HISTORY, com.kesho.generated.tables.StudentHistory.STUDENT_HISTORY.ID);
	}

	private static class UniqueKeys0 extends org.jooq.impl.AbstractKeys {
		public static final org.jooq.UniqueKey<com.kesho.generated.tables.records.ContactRecord> KEY_CONTACT_PRIMARY = createUniqueKey(com.kesho.generated.tables.Contact.CONTACT, com.kesho.generated.tables.Contact.CONTACT.ID);
		public static final org.jooq.UniqueKey<com.kesho.generated.tables.records.ContactDetailRecord> KEY_CONTACT_DETAIL_PRIMARY = createUniqueKey(com.kesho.generated.tables.ContactDetail.CONTACT_DETAIL, com.kesho.generated.tables.ContactDetail.CONTACT_DETAIL.ID);
		public static final org.jooq.UniqueKey<com.kesho.generated.tables.records.EducationHistoryRecord> KEY_EDUCATION_HISTORY_PRIMARY = createUniqueKey(com.kesho.generated.tables.EducationHistory.EDUCATION_HISTORY, com.kesho.generated.tables.EducationHistory.EDUCATION_HISTORY.ID);
		public static final org.jooq.UniqueKey<com.kesho.generated.tables.records.FamilyRecord> KEY_FAMILY_PRIMARY = createUniqueKey(com.kesho.generated.tables.Family.FAMILY, com.kesho.generated.tables.Family.FAMILY.ID);
		public static final org.jooq.UniqueKey<com.kesho.generated.tables.records.FamilyRecord> KEY_FAMILY_FAMILY_NAME_UNIQUE = createUniqueKey(com.kesho.generated.tables.Family.FAMILY, com.kesho.generated.tables.Family.FAMILY.FAMILY_NAME);
		public static final org.jooq.UniqueKey<com.kesho.generated.tables.records.FamilyProfileRecord> KEY_FAMILY_PROFILE_PRIMARY = createUniqueKey(com.kesho.generated.tables.FamilyProfile.FAMILY_PROFILE, com.kesho.generated.tables.FamilyProfile.FAMILY_PROFILE.ID);
		public static final org.jooq.UniqueKey<com.kesho.generated.tables.records.PaymentArrangementsRecord> KEY_PAYMENT_ARRANGEMENTS_PRIMARY = createUniqueKey(com.kesho.generated.tables.PaymentArrangements.PAYMENT_ARRANGEMENTS, com.kesho.generated.tables.PaymentArrangements.PAYMENT_ARRANGEMENTS.ID);
		public static final org.jooq.UniqueKey<com.kesho.generated.tables.records.SchoolsRecord> KEY_SCHOOLS_PRIMARY = createUniqueKey(com.kesho.generated.tables.Schools.SCHOOLS, com.kesho.generated.tables.Schools.SCHOOLS.ID);
		public static final org.jooq.UniqueKey<com.kesho.generated.tables.records.SchoolsRecord> KEY_SCHOOLS_UNIQUE_SCHOOL_NAME = createUniqueKey(com.kesho.generated.tables.Schools.SCHOOLS, com.kesho.generated.tables.Schools.SCHOOLS.NAME);
		public static final org.jooq.UniqueKey<com.kesho.generated.tables.records.SponsorsRecord> KEY_SPONSORS_PRIMARY = createUniqueKey(com.kesho.generated.tables.Sponsors.SPONSORS, com.kesho.generated.tables.Sponsors.SPONSORS.ID);
		public static final org.jooq.UniqueKey<com.kesho.generated.tables.records.StudentsRecord> KEY_STUDENTS_PRIMARY = createUniqueKey(com.kesho.generated.tables.Students.STUDENTS, com.kesho.generated.tables.Students.STUDENTS.ID);
		public static final org.jooq.UniqueKey<com.kesho.generated.tables.records.StudentHistoryRecord> KEY_STUDENT_HISTORY_PRIMARY = createUniqueKey(com.kesho.generated.tables.StudentHistory.STUDENT_HISTORY, com.kesho.generated.tables.StudentHistory.STUDENT_HISTORY.ID);
	}

	private static class ForeignKeys0 extends org.jooq.impl.AbstractKeys {
		public static final org.jooq.ForeignKey<com.kesho.generated.tables.records.ContactRecord, com.kesho.generated.tables.records.SchoolsRecord> CONTACT_IBFK_1 = createForeignKey(com.kesho.generated.Keys.KEY_SCHOOLS_PRIMARY, com.kesho.generated.tables.Contact.CONTACT, com.kesho.generated.tables.Contact.CONTACT.SCHOOL_ID);
		public static final org.jooq.ForeignKey<com.kesho.generated.tables.records.EducationHistoryRecord, com.kesho.generated.tables.records.StudentsRecord> EDUCATION_HISTORY_IBFK_1 = createForeignKey(com.kesho.generated.Keys.KEY_STUDENTS_PRIMARY, com.kesho.generated.tables.EducationHistory.EDUCATION_HISTORY, com.kesho.generated.tables.EducationHistory.EDUCATION_HISTORY.STUDENT_ID);
		public static final org.jooq.ForeignKey<com.kesho.generated.tables.records.EducationHistoryRecord, com.kesho.generated.tables.records.SchoolsRecord> EDUCATION_HISTORY_IBFK_2 = createForeignKey(com.kesho.generated.Keys.KEY_SCHOOLS_PRIMARY, com.kesho.generated.tables.EducationHistory.EDUCATION_HISTORY, com.kesho.generated.tables.EducationHistory.EDUCATION_HISTORY.SCHOOL_ID);
		public static final org.jooq.ForeignKey<com.kesho.generated.tables.records.FamilyProfileRecord, com.kesho.generated.tables.records.FamilyRecord> FK_FAMILY_PROFILE_FAMILY = createForeignKey(com.kesho.generated.Keys.KEY_FAMILY_PRIMARY, com.kesho.generated.tables.FamilyProfile.FAMILY_PROFILE, com.kesho.generated.tables.FamilyProfile.FAMILY_PROFILE.FAMILY_ID);
		public static final org.jooq.ForeignKey<com.kesho.generated.tables.records.PaymentArrangementsRecord, com.kesho.generated.tables.records.SponsorsRecord> PAYMENT_ARRANGEMENTS_IBFK_1 = createForeignKey(com.kesho.generated.Keys.KEY_SPONSORS_PRIMARY, com.kesho.generated.tables.PaymentArrangements.PAYMENT_ARRANGEMENTS, com.kesho.generated.tables.PaymentArrangements.PAYMENT_ARRANGEMENTS.SPONSOR_ID);
		public static final org.jooq.ForeignKey<com.kesho.generated.tables.records.PaymentArrangementsRecord, com.kesho.generated.tables.records.StudentsRecord> PAYMENT_ARRANGEMENTS_IBFK_2 = createForeignKey(com.kesho.generated.Keys.KEY_STUDENTS_PRIMARY, com.kesho.generated.tables.PaymentArrangements.PAYMENT_ARRANGEMENTS, com.kesho.generated.tables.PaymentArrangements.PAYMENT_ARRANGEMENTS.STUDENT_ID);
		public static final org.jooq.ForeignKey<com.kesho.generated.tables.records.StudentsRecord, com.kesho.generated.tables.records.FamilyRecord> FAMILY_STUDENT_FK = createForeignKey(com.kesho.generated.Keys.KEY_FAMILY_PRIMARY, com.kesho.generated.tables.Students.STUDENTS, com.kesho.generated.tables.Students.STUDENTS.FAMILY_ID);
		public static final org.jooq.ForeignKey<com.kesho.generated.tables.records.StudentHistoryRecord, com.kesho.generated.tables.records.StudentsRecord> FK_STUDENT_HISTORY_STUDENT = createForeignKey(com.kesho.generated.Keys.KEY_STUDENTS_PRIMARY, com.kesho.generated.tables.StudentHistory.STUDENT_HISTORY, com.kesho.generated.tables.StudentHistory.STUDENT_HISTORY.STUDENT_ID);
	}
}
