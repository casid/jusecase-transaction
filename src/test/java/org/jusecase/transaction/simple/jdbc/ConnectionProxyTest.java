package org.jusecase.transaction.simple.jdbc;

import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class ConnectionProxyTest {

    private ConnectionProxy connectionProxy;
    private Connection connection;

    @Before
    public void setUp() throws Exception {
        connection = mock(Connection.class);
        connectionProxy = new ConnectionProxy(connection);
    }

    @Test
    public void getConnection() {
        assertSame(connection, connectionProxy.getConnection());
    }

    @Test
    public void createStatement() throws SQLException {
        connectionProxy.createStatement();
        verify(connection).createStatement();

        connectionProxy.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        verify(connection).createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

        connectionProxy.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, ResultSet.HOLD_CURSORS_OVER_COMMIT);
        verify(connection).createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, ResultSet.HOLD_CURSORS_OVER_COMMIT);
    }

    @Test
    @SuppressWarnings("MagicConstant")
    public void prepareStatement() throws SQLException {
        connectionProxy.prepareStatement("statement");
        verify(connection).prepareStatement("statement");

        connectionProxy.prepareStatement("statement", 1);
        verify(connection).prepareStatement("statement", 1);

        connectionProxy.prepareStatement("statement", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        verify(connection).prepareStatement("statement", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

        connectionProxy.prepareStatement("statement", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, ResultSet.HOLD_CURSORS_OVER_COMMIT);
        verify(connection).prepareStatement("statement", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, ResultSet.HOLD_CURSORS_OVER_COMMIT);

        connectionProxy.prepareStatement("statement", new int[]{1, 2});
        verify(connection).prepareStatement("statement", new int[]{1, 2});

        connectionProxy.prepareStatement("statement", new String[]{"a", "b"});
        verify(connection).prepareStatement("statement", new String[]{"a", "b"});
    }

    @Test
    public void prepareCall() throws SQLException {
        connectionProxy.prepareCall("call");
        verify(connection).prepareCall("call");

        connectionProxy.prepareCall("call", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        verify(connection).prepareCall("call", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

        connectionProxy.prepareCall("call", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, ResultSet.HOLD_CURSORS_OVER_COMMIT);
        verify(connection).prepareCall("call", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, ResultSet.HOLD_CURSORS_OVER_COMMIT);
    }

    @Test
    public void nativeSQL() throws SQLException {
        connectionProxy.nativeSQL("sql");
        verify(connection).nativeSQL("sql");
    }

    @Test
    public void setAutoCommit() throws SQLException {
        connectionProxy.setAutoCommit(true);
        verify(connection).setAutoCommit(true);
    }

    @Test
    public void getAutoCommit() throws SQLException {
        connectionProxy.getAutoCommit();
        verify(connection).getAutoCommit();
    }

    @Test
    public void commit() throws SQLException {
        connectionProxy.commit();
        verify(connection).commit();
    }

    @Test
    public void rollback() throws SQLException {
        connectionProxy.rollback();
        verify(connection).rollback();

        connectionProxy.rollback(null);
        verify(connection).rollback(null);
    }

    @Test
    public void close() throws SQLException {
        connectionProxy.close();
        verify(connection, never()).close();
    }

    @Test
    public void isClosed() throws SQLException {
        connectionProxy.isClosed();
        verify(connection).isClosed();
    }

    @Test
    public void getMetaData() throws SQLException {
        connectionProxy.getMetaData();
        verify(connection).getMetaData();
    }

    @Test
    public void setReadOnly() throws SQLException {
        connectionProxy.setReadOnly(true);
        verify(connection).setReadOnly(true);
    }

    @Test
    public void isReadOnly() throws SQLException {
        connectionProxy.isReadOnly();
        verify(connection).isReadOnly();
    }

    @Test
    public void setCatalog() throws SQLException {
        connectionProxy.setCatalog("catalog");
        verify(connection).setCatalog("catalog");
    }

    @Test
    public void getCatalog() throws SQLException {
        connectionProxy.getCatalog();
        verify(connection).getCatalog();
    }

    @Test
    public void setTransactionIsolation() throws SQLException {
        connectionProxy.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        verify(connection).setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
    }

    @Test
    public void getTransactionIsolation() throws SQLException {
        connectionProxy.getTransactionIsolation();
        verify(connection).getTransactionIsolation();
    }

    @Test
    @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
    public void getWarnings() throws SQLException {
        connectionProxy.getWarnings();
        verify(connection).getWarnings();
    }

    @Test
    public void clearWarnings() throws SQLException {
        connectionProxy.clearWarnings();
        verify(connection).clearWarnings();
    }

    @Test
    public void getTypeMap() throws SQLException {
        connectionProxy.getTypeMap();
        verify(connection).getTypeMap();
    }

    @Test
    public void setTypeMap() throws SQLException {
        connectionProxy.setTypeMap(new HashMap<String, Class<?>>());
        verify(connection).setTypeMap(new HashMap<String, Class<?>>());
    }

    @Test
    public void setHoldability() throws SQLException {
        connectionProxy.setHoldability(2);
        verify(connection).setHoldability(2);
    }

    @Test
    public void getHoldability() throws SQLException {
        connectionProxy.getHoldability();
        verify(connection).getHoldability();
    }

    @Test
    public void setSavepoint() throws SQLException {
        connectionProxy.setSavepoint();
        verify(connection).setSavepoint();

        connectionProxy.setSavepoint("savepoint");
        verify(connection).setSavepoint("savepoint");
    }

    @Test
    public void releaseSavepoint() throws SQLException {
        connectionProxy.releaseSavepoint(null);
        verify(connection).releaseSavepoint(null);
    }

    @Test
    public void createClob() throws SQLException {
        connectionProxy.createClob();
        verify(connection).createClob();
    }

    @Test
    public void createBlob() throws SQLException {
        connectionProxy.createBlob();
        verify(connection).createBlob();
    }

    @Test
    public void createNClob() throws SQLException {
        connectionProxy.createNClob();
        verify(connection).createNClob();
    }

    @Test
    public void createSQLXML() throws SQLException {
        connectionProxy.createSQLXML();
        verify(connection).createSQLXML();
    }

    @Test
    public void isValid() throws SQLException {
        connectionProxy.isValid(100);
        verify(connection).isValid(100);
    }

    @Test
    public void setClientInfo() throws SQLException {
        Properties properties = new Properties();
        connectionProxy.setClientInfo(properties);
        verify(connection).setClientInfo(properties);

        connectionProxy.setClientInfo("key", "value");
        verify(connection).setClientInfo("key", "value");
    }

    @Test
    public void getClientInfo() throws SQLException {
        connectionProxy.getClientInfo();
        verify(connection).getClientInfo();

        connectionProxy.getClientInfo("value");
        verify(connection).getClientInfo("value");
    }

    @Test
    public void createArrayOf() throws SQLException {
        connectionProxy.createArrayOf("type", new Object[]{});
        verify(connection).createArrayOf("type", new Object[]{});
    }

    @Test
    public void createStruct() throws SQLException {
        connectionProxy.createStruct("type", new Object[]{});
        verify(connection).createStruct("type", new Object[]{});
    }

    @Test
    public void setSchema() throws SQLException {
        connectionProxy.setSchema("1234");
        verify(connection).setSchema("1234");
    }

    @Test
    public void getSchema() throws SQLException {
        connectionProxy.getSchema();
        verify(connection).getSchema();
    }

    @Test
    public void abort() throws SQLException {
        connectionProxy.abort(null);
        verify(connection).abort(null);
    }

    @Test
    public void setNetworkTimeout() throws SQLException {
        connectionProxy.setNetworkTimeout(null, 1000);
        verify(connection).setNetworkTimeout(null, 1000);
    }

    @Test
    public void getNetworkTimeout() throws SQLException {
        connectionProxy.getNetworkTimeout();
        verify(connection).getNetworkTimeout();
    }

    @Test
    public void unwrap() throws SQLException {
        connectionProxy.unwrap(String.class);
        verify(connection).unwrap(String.class);
    }

    @Test
    public void isWrapperFor() throws SQLException {
        connectionProxy.isWrapperFor(String.class);
        verify(connection).isWrapperFor(String.class);
    }
}