package com.kesho.matrix.dbtest;

import static com.google.common.base.Preconditions.checkNotNull;

import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;

/**
 * Custom {@code IDatabaseTester} to get rid of the warnings about using the {@code DefaultDataTypeFactory}.
 */
public class DataTypeFactoryAwareJdbcDatabaseTester extends JdbcDatabaseTester {
	private final MySqlDataTypeFactory factory = new MySqlDataTypeFactory();

    public DataTypeFactoryAwareJdbcDatabaseTester(String driverClass, String connectionUrl, String username, String password) throws ClassNotFoundException {
        super(driverClass, connectionUrl, username, password);
    }

    @Override
    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    public IDatabaseConnection getConnection() throws Exception {
    	IDatabaseConnection connection = super.getConnection();
        checkNotNull(connection, "connection");

        DatabaseConfig config = connection.getConfig();
        config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, factory);

        return connection;
    }
}
