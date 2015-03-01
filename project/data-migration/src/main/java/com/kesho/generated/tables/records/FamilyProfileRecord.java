/**
 * This class is generated by jOOQ
 */
package com.kesho.generated.tables.records;

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
public class FamilyProfileRecord extends org.jooq.impl.UpdatableRecordImpl<com.kesho.generated.tables.records.FamilyProfileRecord> implements org.jooq.Record4<java.lang.Long, java.lang.Long, java.sql.Date, java.lang.String> {

	private static final long serialVersionUID = 755940228;

	/**
	 * Setter for <code>kesho.FAMILY_PROFILE.ID</code>.
	 */
	public void setId(java.lang.Long value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>kesho.FAMILY_PROFILE.ID</code>.
	 */
	public java.lang.Long getId() {
		return (java.lang.Long) getValue(0);
	}

	/**
	 * Setter for <code>kesho.FAMILY_PROFILE.FAMILY_ID</code>.
	 */
	public void setFamilyId(java.lang.Long value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>kesho.FAMILY_PROFILE.FAMILY_ID</code>.
	 */
	public java.lang.Long getFamilyId() {
		return (java.lang.Long) getValue(1);
	}

	/**
	 * Setter for <code>kesho.FAMILY_PROFILE.DATE</code>.
	 */
	public void setDate(java.sql.Date value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>kesho.FAMILY_PROFILE.DATE</code>.
	 */
	public java.sql.Date getDate() {
		return (java.sql.Date) getValue(2);
	}

	/**
	 * Setter for <code>kesho.FAMILY_PROFILE.DETAILS</code>.
	 */
	public void setDetails(java.lang.String value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>kesho.FAMILY_PROFILE.DETAILS</code>.
	 */
	public java.lang.String getDetails() {
		return (java.lang.String) getValue(3);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Record1<java.lang.Long> key() {
		return (org.jooq.Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record4 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row4<java.lang.Long, java.lang.Long, java.sql.Date, java.lang.String> fieldsRow() {
		return (org.jooq.Row4) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row4<java.lang.Long, java.lang.Long, java.sql.Date, java.lang.String> valuesRow() {
		return (org.jooq.Row4) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Long> field1() {
		return com.kesho.generated.tables.FamilyProfile.FAMILY_PROFILE.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Long> field2() {
		return com.kesho.generated.tables.FamilyProfile.FAMILY_PROFILE.FAMILY_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.sql.Date> field3() {
		return com.kesho.generated.tables.FamilyProfile.FAMILY_PROFILE.DATE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field4() {
		return com.kesho.generated.tables.FamilyProfile.FAMILY_PROFILE.DETAILS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Long value1() {
		return getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Long value2() {
		return getFamilyId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.sql.Date value3() {
		return getDate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value4() {
		return getDetails();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public FamilyProfileRecord value1(java.lang.Long value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public FamilyProfileRecord value2(java.lang.Long value) {
		setFamilyId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public FamilyProfileRecord value3(java.sql.Date value) {
		setDate(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public FamilyProfileRecord value4(java.lang.String value) {
		setDetails(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public FamilyProfileRecord values(java.lang.Long value1, java.lang.Long value2, java.sql.Date value3, java.lang.String value4) {
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached FamilyProfileRecord
	 */
	public FamilyProfileRecord() {
		super(com.kesho.generated.tables.FamilyProfile.FAMILY_PROFILE);
	}

	/**
	 * Create a detached, initialised FamilyProfileRecord
	 */
	public FamilyProfileRecord(java.lang.Long id, java.lang.Long familyId, java.sql.Date date, java.lang.String details) {
		super(com.kesho.generated.tables.FamilyProfile.FAMILY_PROFILE);

		setValue(0, id);
		setValue(1, familyId);
		setValue(2, date);
		setValue(3, details);
	}
}