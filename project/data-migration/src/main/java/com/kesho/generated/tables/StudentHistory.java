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
public class StudentHistory extends org.jooq.impl.TableImpl<com.kesho.generated.tables.records.StudentHistoryRecord> {

	private static final long serialVersionUID = 1734956861;

	/**
	 * The reference instance of <code>kesho.STUDENT_HISTORY</code>
	 */
	public static final com.kesho.generated.tables.StudentHistory STUDENT_HISTORY = new com.kesho.generated.tables.StudentHistory();

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<com.kesho.generated.tables.records.StudentHistoryRecord> getRecordType() {
		return com.kesho.generated.tables.records.StudentHistoryRecord.class;
	}

	/**
	 * The column <code>kesho.STUDENT_HISTORY.ID</code>.
	 */
	public final org.jooq.TableField<com.kesho.generated.tables.records.StudentHistoryRecord, java.lang.Long> ID = createField("ID", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

	/**
	 * The column <code>kesho.STUDENT_HISTORY.STUDENT_ID</code>.
	 */
	public final org.jooq.TableField<com.kesho.generated.tables.records.StudentHistoryRecord, java.lang.Long> STUDENT_ID = createField("STUDENT_ID", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

	/**
	 * The column <code>kesho.STUDENT_HISTORY.DETAILS</code>.
	 */
	public final org.jooq.TableField<com.kesho.generated.tables.records.StudentHistoryRecord, java.lang.String> DETAILS = createField("DETAILS", org.jooq.impl.SQLDataType.VARCHAR.length(1150).nullable(false), this, "");

	/**
	 * The column <code>kesho.STUDENT_HISTORY.DATE</code>.
	 */
	public final org.jooq.TableField<com.kesho.generated.tables.records.StudentHistoryRecord, java.sql.Date> DATE = createField("DATE", org.jooq.impl.SQLDataType.DATE, this, "");

	/**
	 * Create a <code>kesho.STUDENT_HISTORY</code> table reference
	 */
	public StudentHistory() {
		this("STUDENT_HISTORY", null);
	}

	/**
	 * Create an aliased <code>kesho.STUDENT_HISTORY</code> table reference
	 */
	public StudentHistory(java.lang.String alias) {
		this(alias, com.kesho.generated.tables.StudentHistory.STUDENT_HISTORY);
	}

	private StudentHistory(java.lang.String alias, org.jooq.Table<com.kesho.generated.tables.records.StudentHistoryRecord> aliased) {
		this(alias, aliased, null);
	}

	private StudentHistory(java.lang.String alias, org.jooq.Table<com.kesho.generated.tables.records.StudentHistoryRecord> aliased, org.jooq.Field<?>[] parameters) {
		super(alias, com.kesho.generated.Kesho.KESHO, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Identity<com.kesho.generated.tables.records.StudentHistoryRecord, java.lang.Long> getIdentity() {
		return com.kesho.generated.Keys.IDENTITY_STUDENT_HISTORY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.UniqueKey<com.kesho.generated.tables.records.StudentHistoryRecord> getPrimaryKey() {
		return com.kesho.generated.Keys.KEY_STUDENT_HISTORY_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.UniqueKey<com.kesho.generated.tables.records.StudentHistoryRecord>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<com.kesho.generated.tables.records.StudentHistoryRecord>>asList(com.kesho.generated.Keys.KEY_STUDENT_HISTORY_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.ForeignKey<com.kesho.generated.tables.records.StudentHistoryRecord, ?>> getReferences() {
		return java.util.Arrays.<org.jooq.ForeignKey<com.kesho.generated.tables.records.StudentHistoryRecord, ?>>asList(com.kesho.generated.Keys.FK_STUDENT_HISTORY_STUDENT);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public com.kesho.generated.tables.StudentHistory as(java.lang.String alias) {
		return new com.kesho.generated.tables.StudentHistory(alias, this);
	}

	/**
	 * Rename this table
	 */
	public com.kesho.generated.tables.StudentHistory rename(java.lang.String name) {
		return new com.kesho.generated.tables.StudentHistory(name, null);
	}
}
