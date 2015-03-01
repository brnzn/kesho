/**
 * This class is generated by jOOQ
 */
package com.kesho.generated;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.5.3"
	},
	comments = "This class is generated by jOOQ"
)
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Kesho extends org.jooq.impl.SchemaImpl {

	private static final long serialVersionUID = -664072428;

	/**
	 * The reference instance of <code>kesho</code>
	 */
	public static final Kesho KESHO = new Kesho();

	/**
	 * No further instances allowed
	 */
	private Kesho() {
		super("kesho");
	}

	@Override
	public final java.util.List<org.jooq.Table<?>> getTables() {
		java.util.List result = new java.util.ArrayList();
		result.addAll(getTables0());
		return result;
	}

	private final java.util.List<org.jooq.Table<?>> getTables0() {
		return java.util.Arrays.<org.jooq.Table<?>>asList(
			com.kesho.generated.tables.Contact.CONTACT,
			com.kesho.generated.tables.ContactDetail.CONTACT_DETAIL,
			com.kesho.generated.tables.EducationHistory.EDUCATION_HISTORY,
			com.kesho.generated.tables.Family.FAMILY,
			com.kesho.generated.tables.FamilyProfile.FAMILY_PROFILE,
			com.kesho.generated.tables.PaymentArrangements.PAYMENT_ARRANGEMENTS,
			com.kesho.generated.tables.Schools.SCHOOLS,
			com.kesho.generated.tables.Sponsors.SPONSORS,
			com.kesho.generated.tables.Students.STUDENTS,
			com.kesho.generated.tables.StudentHistory.STUDENT_HISTORY);
	}
}