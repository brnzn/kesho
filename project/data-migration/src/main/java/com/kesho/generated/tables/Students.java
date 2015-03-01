/**
 * This class is generated by jOOQ
 */
package com.kesho.generated.tables;

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
public class Students extends org.jooq.impl.TableImpl<com.kesho.generated.tables.records.StudentsRecord> {

	private static final long serialVersionUID = 1227595174;

	/**
	 * The reference instance of <code>kesho.STUDENTS</code>
	 */
	public static final com.kesho.generated.tables.Students STUDENTS = new com.kesho.generated.tables.Students();

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<com.kesho.generated.tables.records.StudentsRecord> getRecordType() {
		return com.kesho.generated.tables.records.StudentsRecord.class;
	}

	/**
	 * The column <code>kesho.STUDENTS.ID</code>.
	 */
	public final org.jooq.TableField<com.kesho.generated.tables.records.StudentsRecord, java.lang.Long> ID = createField("ID", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

	/**
	 * The column <code>kesho.STUDENTS.NAME</code>.
	 */
	public final org.jooq.TableField<com.kesho.generated.tables.records.StudentsRecord, java.lang.String> NAME = createField("NAME", org.jooq.impl.SQLDataType.VARCHAR.length(20), this, "");

	/**
	 * The column <code>kesho.STUDENTS.GENDER</code>.
	 */
	public final org.jooq.TableField<com.kesho.generated.tables.records.StudentsRecord, java.lang.String> GENDER = createField("GENDER", org.jooq.impl.SQLDataType.CHAR.length(1), this, "");

	/**
	 * The column <code>kesho.STUDENTS.YEAR_OF_BIRTH</code>. YYYY
	 */
	public final org.jooq.TableField<com.kesho.generated.tables.records.StudentsRecord, org.jooq.types.UShort> YEAR_OF_BIRTH = createField("YEAR_OF_BIRTH", org.jooq.impl.SQLDataType.SMALLINTUNSIGNED, this, "YYYY");

	/**
	 * The column <code>kesho.STUDENTS.DISABILITY</code>.
	 */
	public final org.jooq.TableField<com.kesho.generated.tables.records.StudentsRecord, org.jooq.types.UByte> DISABILITY = createField("DISABILITY", org.jooq.impl.SQLDataType.TINYINTUNSIGNED, this, "");

	/**
	 * The column <code>kesho.STUDENTS.CONTACT_NUMBER</code>.
	 */
	public final org.jooq.TableField<com.kesho.generated.tables.records.StudentsRecord, java.lang.String> CONTACT_NUMBER = createField("CONTACT_NUMBER", org.jooq.impl.SQLDataType.VARCHAR.length(15), this, "");

	/**
	 * The column <code>kesho.STUDENTS.HOME_LOCATION</code>.
	 */
	public final org.jooq.TableField<com.kesho.generated.tables.records.StudentsRecord, java.lang.String> HOME_LOCATION = createField("HOME_LOCATION", org.jooq.impl.SQLDataType.VARCHAR.length(20), this, "");

	/**
	 * The column <code>kesho.STUDENTS.ENRICHMENT_SUPPORT</code>. YES/NO
	 */
	public final org.jooq.TableField<com.kesho.generated.tables.records.StudentsRecord, org.jooq.types.UByte> ENRICHMENT_SUPPORT = createField("ENRICHMENT_SUPPORT", org.jooq.impl.SQLDataType.TINYINTUNSIGNED, this, "YES/NO");

	/**
	 * The column <code>kesho.STUDENTS.FINANCIAL_SUPPORT_STATUS</code>. Graduated more than 2 years ago or Sponsorship withdrawn
	 */
	public final org.jooq.TableField<com.kesho.generated.tables.records.StudentsRecord, java.lang.String> FINANCIAL_SUPPORT_STATUS = createField("FINANCIAL_SUPPORT_STATUS", org.jooq.impl.SQLDataType.VARCHAR.length(15), this, "Graduated more than 2 years ago or Sponsorship withdrawn");

	/**
	 * The column <code>kesho.STUDENTS.FINANCIAL_SUPPORT</code>. Yes / No / On hold
	 */
	public final org.jooq.TableField<com.kesho.generated.tables.records.StudentsRecord, org.jooq.types.UByte> FINANCIAL_SUPPORT = createField("FINANCIAL_SUPPORT", org.jooq.impl.SQLDataType.TINYINTUNSIGNED, this, "Yes / No / On hold");

	/**
	 * The column <code>kesho.STUDENTS.FINANCIAL_SUPPORT_STATUS_DETAILS</code>.
	 */
	public final org.jooq.TableField<com.kesho.generated.tables.records.StudentsRecord, java.lang.String> FINANCIAL_SUPPORT_STATUS_DETAILS = createField("FINANCIAL_SUPPORT_STATUS_DETAILS", org.jooq.impl.SQLDataType.VARCHAR.length(50), this, "");

	/**
	 * The column <code>kesho.STUDENTS.EMAIL</code>.
	 */
	public final org.jooq.TableField<com.kesho.generated.tables.records.StudentsRecord, java.lang.String> EMAIL = createField("EMAIL", org.jooq.impl.SQLDataType.VARCHAR.length(40), this, "");

	/**
	 * The column <code>kesho.STUDENTS.FACEBOOK</code>.
	 */
	public final org.jooq.TableField<com.kesho.generated.tables.records.StudentsRecord, java.lang.String> FACEBOOK = createField("FACEBOOK", org.jooq.impl.SQLDataType.VARCHAR.length(40), this, "");

	/**
	 * The column <code>kesho.STUDENTS.START_DATE</code>.
	 */
	public final org.jooq.TableField<com.kesho.generated.tables.records.StudentsRecord, java.sql.Date> START_DATE = createField("START_DATE", org.jooq.impl.SQLDataType.DATE, this, "");

	/**
	 * The column <code>kesho.STUDENTS.LEVEL_OF_SUPPORT</code>. Kesho agreed level of Financial Support
	 */
	public final org.jooq.TableField<com.kesho.generated.tables.records.StudentsRecord, java.lang.String> LEVEL_OF_SUPPORT = createField("LEVEL_OF_SUPPORT", org.jooq.impl.SQLDataType.VARCHAR.length(20), this, "Kesho agreed level of Financial Support");

	/**
	 * The column <code>kesho.STUDENTS.ALUMNI_MEMBER</code>.
	 */
	public final org.jooq.TableField<com.kesho.generated.tables.records.StudentsRecord, org.jooq.types.UInteger> ALUMNI_MEMBER = createField("ALUMNI_MEMBER", org.jooq.impl.SQLDataType.INTEGERUNSIGNED, this, "");

	/**
	 * The column <code>kesho.STUDENTS.FAMILY_ID</code>.
	 */
	public final org.jooq.TableField<com.kesho.generated.tables.records.StudentsRecord, java.lang.Long> FAMILY_ID = createField("FAMILY_ID", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

	/**
	 * The column <code>kesho.STUDENTS.LEAVER_STATUS</code>.
	 */
	public final org.jooq.TableField<com.kesho.generated.tables.records.StudentsRecord, java.lang.String> LEAVER_STATUS = createField("LEAVER_STATUS", org.jooq.impl.SQLDataType.VARCHAR.length(33), this, "");

	/**
	 * The column <code>kesho.STUDENTS.TOTAL_SPONSORSHIP_REQUIRED</code>.
	 */
	public final org.jooq.TableField<com.kesho.generated.tables.records.StudentsRecord, org.jooq.types.UInteger> TOTAL_SPONSORSHIP_REQUIRED = createField("TOTAL_SPONSORSHIP_REQUIRED", org.jooq.impl.SQLDataType.INTEGERUNSIGNED, this, "");

	/**
	 * The column <code>kesho.STUDENTS.END_DATE</code>.
	 */
	public final org.jooq.TableField<com.kesho.generated.tables.records.StudentsRecord, java.sql.Date> END_DATE = createField("END_DATE", org.jooq.impl.SQLDataType.DATE, this, "");

	/**
	 * The column <code>kesho.STUDENTS.FINANCIAL_SUPPORT_STATUS_SUB_DETAILS</code>.
	 */
	public final org.jooq.TableField<com.kesho.generated.tables.records.StudentsRecord, java.lang.String> FINANCIAL_SUPPORT_STATUS_SUB_DETAILS = createField("FINANCIAL_SUPPORT_STATUS_SUB_DETAILS", org.jooq.impl.SQLDataType.VARCHAR.length(45), this, "");

	/**
	 * The column <code>kesho.STUDENTS.VERSION</code>.
	 */
	public final org.jooq.TableField<com.kesho.generated.tables.records.StudentsRecord, java.lang.Integer> VERSION = createField("VERSION", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "");

	/**
	 * Create a <code>kesho.STUDENTS</code> table reference
	 */
	public Students() {
		this("STUDENTS", null);
	}

	/**
	 * Create an aliased <code>kesho.STUDENTS</code> table reference
	 */
	public Students(java.lang.String alias) {
		this(alias, com.kesho.generated.tables.Students.STUDENTS);
	}

	private Students(java.lang.String alias, org.jooq.Table<com.kesho.generated.tables.records.StudentsRecord> aliased) {
		this(alias, aliased, null);
	}

	private Students(java.lang.String alias, org.jooq.Table<com.kesho.generated.tables.records.StudentsRecord> aliased, org.jooq.Field<?>[] parameters) {
		super(alias, com.kesho.generated.Kesho.KESHO, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Identity<com.kesho.generated.tables.records.StudentsRecord, java.lang.Long> getIdentity() {
		return com.kesho.generated.Keys.IDENTITY_STUDENTS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.UniqueKey<com.kesho.generated.tables.records.StudentsRecord> getPrimaryKey() {
		return com.kesho.generated.Keys.KEY_STUDENTS_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.UniqueKey<com.kesho.generated.tables.records.StudentsRecord>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<com.kesho.generated.tables.records.StudentsRecord>>asList(com.kesho.generated.Keys.KEY_STUDENTS_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.ForeignKey<com.kesho.generated.tables.records.StudentsRecord, ?>> getReferences() {
		return java.util.Arrays.<org.jooq.ForeignKey<com.kesho.generated.tables.records.StudentsRecord, ?>>asList(com.kesho.generated.Keys.FAMILY_STUDENT_FK);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public com.kesho.generated.tables.Students as(java.lang.String alias) {
		return new com.kesho.generated.tables.Students(alias, this);
	}

	/**
	 * Rename this table
	 */
	public com.kesho.generated.tables.Students rename(java.lang.String name) {
		return new com.kesho.generated.tables.Students(name, null);
	}
}