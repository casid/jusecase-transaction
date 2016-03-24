package org.jusecase.transaction.simple.jdbc;

import org.junit.Before;
import org.junit.Test;
import org.jusecase.transaction.simple.TransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

public class DataSourceProxyTest {

    private DataSourceProxy dataSourceProxy;
    private DataSource dataSource;
    private TransactionManager transactionManager;

    @Before
    public void setUp() throws Exception {
        dataSource = mock(DataSource.class);
        transactionManager = mock(TransactionManager.class);
        dataSourceProxy = new DataSourceProxy(dataSource, transactionManager);
    }

    @Test
    public void getConnection_noTransaction() throws SQLException {
        dataSourceProxy.getConnection();
        verify(dataSource).getConnection();

        dataSourceProxy.getConnection("user", "password");
        verify(dataSource).getConnection("user", "password");
    }

    @Test
    public void getConnection_transaction() throws SQLException {
        DataSourceTransaction transaction = mock(DataSourceTransaction.class);
        when(transactionManager.getCurrent()).thenReturn(transaction);

        dataSourceProxy.getConnection();
        verify(transaction).getConnection();

        reset(transaction);

        dataSourceProxy.getConnection("user", "password");
        verify(transaction).getConnection();
    }

    @Test
    public void getLogWriter() throws SQLException {
        dataSourceProxy.getLogWriter();
        verify(dataSource).getLogWriter();
    }

    @Test
    public void setLogWriter() throws SQLException {
        dataSourceProxy.setLogWriter(null);
        verify(dataSource).setLogWriter(null);
    }

    @Test
    public void setLoginTimeout() throws SQLException {
        dataSourceProxy.setLoginTimeout(100);
        verify(dataSource).setLoginTimeout(100);
    }

    @Test
    public void getLoginTimeout() throws SQLException {
        dataSourceProxy.getLoginTimeout();
        verify(dataSource).getLoginTimeout();
    }

    @Test
    public void getParentLogger() throws SQLException {
        dataSourceProxy.getParentLogger();
        verify(dataSource).getParentLogger();
    }

    @Test
    public void unwrap() throws SQLException {
        dataSourceProxy.unwrap(String.class);
        verify(dataSource).unwrap(String.class);
    }

    @Test
    public void isWrapperFor() throws SQLException {
        dataSourceProxy.isWrapperFor(String.class);
        verify(dataSource).isWrapperFor(String.class);
    }
}