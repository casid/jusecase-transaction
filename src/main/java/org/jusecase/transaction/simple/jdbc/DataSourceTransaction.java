package org.jusecase.transaction.simple.jdbc;

import org.jusecase.transaction.TransactionError;
import org.jusecase.transaction.simple.Transaction;
import org.jusecase.transaction.simple.TransactionManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DataSourceTransaction implements Transaction {
    private final TransactionManager transactionManager;
    private final ConnectionProxy connectionProxy;
    private final boolean initialAutoCommitEnabled;

    public DataSourceTransaction(DataSource dataSource, TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
        try {
            connectionProxy = new ConnectionProxy(dataSource.getConnection());
            initialAutoCommitEnabled = connectionProxy.getAutoCommit();
            connectionProxy.setAutoCommit(false);
        } catch (SQLException e) {
            closeConnectionQuietly();
            throw new TransactionError("Failed to create transaction.", e);
        }
    }

    @Override
    public void rollback() {
        try {
            connectionProxy.rollback();
        } catch (SQLException e) {
            throw new TransactionError("Failed to rollback transaction.", e);
        } finally {
            closeTransaction();
        }
    }

    @Override
    public void commit() {
        try {
            connectionProxy.commit();
            closeTransaction();
        } catch (SQLException e) {
            throw new TransactionError("Failed to commit transaction.", e);
        }
    }

    private void closeTransaction() {
        transactionManager.setCurrent(null);
        closeConnectionQuietly();
    }

    private void closeConnectionQuietly() {
        if (connectionProxy != null) {
            try {
                Connection connection = connectionProxy.getConnection();
                try {
                    connection.setAutoCommit(initialAutoCommitEnabled);
                } finally {
                    connection.close();
                }
            } catch (SQLException e) {
                // Connection is closed quietly.
            }
        }
    }

    public Connection getConnection() {
        return connectionProxy;
    }
}
