package com.kesho.matrix.dbtest;

import static com.google.common.collect.Maps.newHashMap;
import static java.lang.String.format;
import static java.util.Collections.EMPTY_MAP;
import static org.dbunit.operation.DatabaseOperation.DELETE_ALL;

import java.io.InputStream;
import java.util.Date;
import java.util.Map;

import org.dbunit.IDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.dataset.xml.FlatXmlProducer;
import org.junit.rules.ExternalResource;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;

public final class DatabaseSetupRule extends ExternalResource {

    private static final String KESHO_EMPTY_DB = "/kesho-empty-db.xml";

	private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseSetupRule.class);

    /**
     * Empty dataset used to clear out the database before the test and after the test.
     */
    private IDataSet emptyDataSet;
    /**
     * The actual test dataset which is applied in an empty database for the test to execute.
     */
    private IDataSet replacementDataSet;

    /**
     * Empty datasets
     */
    private IDatabaseTester databaseTester;

    private static final String CONNECTION_URL = "jdbc:mysql://localhost/%s";
    private static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";
    private static final String PASSWORD = "admin";
    private static final String USERNAME = "admin";

    private final String databaseName;
    private final String dataFileName;

    public static DatabaseSetupRule setUpDataFor(String dbName, String dataFileName) {
        return new DatabaseSetupRule(dbName, dataFileName);
    }

    public static DatabaseSetupRule setUpDataFor(String dbName) {
        return new DatabaseSetupRule(dbName);
    }

    public static DatabaseSetupRule setupDataFor(String dbName, IDatabaseTester databaseTester) {
        return new DatabaseSetupRule(dbName, databaseTester, null);
    }

    private DatabaseSetupRule(String databaseName, IDatabaseTester databaseTester, String dataFileName) {
        this.databaseName = databaseName;
        this.databaseTester = databaseTester;
        this.dataFileName = dataFileName;
    }

    private DatabaseSetupRule(String databaseName) {
        this(databaseName, null);
    }

    private DatabaseSetupRule(String databaseName, String dataFileName) {
        this(databaseName, null, dataFileName);
    }

    @Override
    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    public void before() throws Exception {
        if (databaseTester == null) {
            databaseTester = new DataTypeFactoryAwareJdbcDatabaseTester(DRIVER_CLASS, format(
                    CONNECTION_URL, databaseName), USERNAME, PASSWORD);
        }

        databaseTester.setDataSet(emptyDataSet);
        databaseTester.onSetup();
        databaseTester.onTearDown();

        databaseTester.setDataSet(replacementDataSet);
        databaseTester.setTearDownOperation(DELETE_ALL);
        databaseTester.onSetup();
    }

    @Override
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    public void after() {
        try {
            databaseTester.setDataSet(emptyDataSet);
            databaseTester.onTearDown();
        } catch (Exception e) {
            LOGGER.error("Failed tear down", e);
            throw new DataSetupRuleException("Failed tear down", e);
        }
    }

    @Override
    public Statement apply(Statement base, Description description) {
        final Class<?> testClass = description.getTestClass();

        String dataFile = getDataFilename(testClass);
        final InputStream inputStream = testClass.getResourceAsStream(dataFile);

        try {
            Map<Object, Object> replacementObjects = getReplacementObjectsMap();

            /**
             * Allows XML dataset files to be written without having to specify the first table entry having all its defined columns
             */
            FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
            builder.setColumnSensing(true);

            emptyDataSet = new FlatXmlDataSet(new FlatXmlProducer(new InputSource(testClass.getResourceAsStream(KESHO_EMPTY_DB))));
            replacementDataSet = new ReplacementDataSet(builder.build((new InputSource(inputStream))), replacementObjects, EMPTY_MAP);

            LOGGER.debug("Empty dataset: {}, replacement dataset: {}", KESHO_EMPTY_DB, dataFile);
        } catch (DataSetException e) {
            throw new DataSetupRuleException("Error obtaining data setup for: " + dataFile, e);
        }

        return super.apply(base, description);
    }

    private String getDataFilename(final Class<?> testClass) {
        String dataFile = dataFileName;
        if (dataFile == null) {
            dataFile = testClass.getSimpleName() + ".xml";
        }
        return dataFile;
    }

    private Map<Object, Object> getReplacementObjectsMap() {
        /**
         * Register placeholder replacement values.
         * Allows a column with a value of "CURDATE()" to be replace with the actual current date.
         */
        Map<Object, Object> replacementObjects = newHashMap();
        replacementObjects.put("CURDATE()", new Date());
        replacementObjects.put("now()", new Date());
        replacementObjects.put("NULL", null);
        replacementObjects.put("null", null);
        return replacementObjects;
    }

    /**
     * Useful for determining the state of the data in the database independently of the persistence mechanism being used by the code you are testing
     *
     * @return IDatabaseConnection
     */
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    public IDatabaseConnection getConnection() {

        IDatabaseConnection databaseConnection;
        try {
            databaseConnection = databaseTester.getConnection();
        } catch (Exception ex) {
            throw new DataSetupRuleException("Error getting database connection with dbUnit", ex);
        }
        return databaseConnection;
    }

    public static class DataSetupRuleException extends RuntimeException {

        private static final long serialVersionUID = 2161428589225037376L;

        public DataSetupRuleException(String message, Exception cause) {
            super(message, cause);
        }
    }
}
