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
public class ContactRecord extends org.jooq.impl.UpdatableRecordImpl<com.kesho.generated.tables.records.ContactRecord> implements org.jooq.Record6<java.lang.Long, java.lang.Long, java.lang.String, java.lang.String, java.lang.String, java.lang.String> {

	private static final long serialVersionUID = 6191777;

	/**
	 * Setter for <code>kesho.CONTACT.ID</code>.
	 */
	public void setId(java.lang.Long value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>kesho.CONTACT.ID</code>.
	 */
	public java.lang.Long getId() {
		return (java.lang.Long) getValue(0);
	}

	/**
	 * Setter for <code>kesho.CONTACT.SCHOOL_ID</code>.
	 */
	public void setSchoolId(java.lang.Long value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>kesho.CONTACT.SCHOOL_ID</code>.
	 */
	public java.lang.Long getSchoolId() {
		return (java.lang.Long) getValue(1);
	}

	/**
	 * Setter for <code>kesho.CONTACT.TITLE</code>.
	 */
	public void setTitle(java.lang.String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>kesho.CONTACT.TITLE</code>.
	 */
	public java.lang.String getTitle() {
		return (java.lang.String) getValue(2);
	}

	/**
	 * Setter for <code>kesho.CONTACT.NAME</code>.
	 */
	public void setName(java.lang.String value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>kesho.CONTACT.NAME</code>.
	 */
	public java.lang.String getName() {
		return (java.lang.String) getValue(3);
	}

	/**
	 * Setter for <code>kesho.CONTACT.SURNAME</code>.
	 */
	public void setSurname(java.lang.String value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>kesho.CONTACT.SURNAME</code>.
	 */
	public java.lang.String getSurname() {
		return (java.lang.String) getValue(4);
	}

	/**
	 * Setter for <code>kesho.CONTACT.JOB_TITLE</code>.
	 */
	public void setJobTitle(java.lang.String value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>kesho.CONTACT.JOB_TITLE</code>.
	 */
	public java.lang.String getJobTitle() {
		return (java.lang.String) getValue(5);
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
	// Record6 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row6<java.lang.Long, java.lang.Long, java.lang.String, java.lang.String, java.lang.String, java.lang.String> fieldsRow() {
		return (org.jooq.Row6) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row6<java.lang.Long, java.lang.Long, java.lang.String, java.lang.String, java.lang.String, java.lang.String> valuesRow() {
		return (org.jooq.Row6) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Long> field1() {
		return com.kesho.generated.tables.Contact.CONTACT.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Long> field2() {
		return com.kesho.generated.tables.Contact.CONTACT.SCHOOL_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field3() {
		return com.kesho.generated.tables.Contact.CONTACT.TITLE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field4() {
		return com.kesho.generated.tables.Contact.CONTACT.NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field5() {
		return com.kesho.generated.tables.Contact.CONTACT.SURNAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field6() {
		return com.kesho.generated.tables.Contact.CONTACT.JOB_TITLE;
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
		return getSchoolId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value3() {
		return getTitle();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value4() {
		return getName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value5() {
		return getSurname();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value6() {
		return getJobTitle();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ContactRecord value1(java.lang.Long value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ContactRecord value2(java.lang.Long value) {
		setSchoolId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ContactRecord value3(java.lang.String value) {
		setTitle(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ContactRecord value4(java.lang.String value) {
		setName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ContactRecord value5(java.lang.String value) {
		setSurname(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ContactRecord value6(java.lang.String value) {
		setJobTitle(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ContactRecord values(java.lang.Long value1, java.lang.Long value2, java.lang.String value3, java.lang.String value4, java.lang.String value5, java.lang.String value6) {
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached ContactRecord
	 */
	public ContactRecord() {
		super(com.kesho.generated.tables.Contact.CONTACT);
	}

	/**
	 * Create a detached, initialised ContactRecord
	 */
	public ContactRecord(java.lang.Long id, java.lang.Long schoolId, java.lang.String title, java.lang.String name, java.lang.String surname, java.lang.String jobTitle) {
		super(com.kesho.generated.tables.Contact.CONTACT);

		setValue(0, id);
		setValue(1, schoolId);
		setValue(2, title);
		setValue(3, name);
		setValue(4, surname);
		setValue(5, jobTitle);
	}
}
