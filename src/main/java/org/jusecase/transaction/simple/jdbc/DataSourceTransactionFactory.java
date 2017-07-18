package org.jusecase.transaction.simple.jdbc;

import org.jusecase.transaction.Transaction;
import org.jusecase.transaction.simple.TransactionFactory;
import org.jusecase.transaction.simple.TransactionManager;

import javax.sql.DataSource;

public class DataSourceTransactionFactory implements TransactionFactory {
    private final DataSource dataSource;
    private final TransactionManager transactionManager;

    public DataSourceTransactionFactory(DataSource dataSource, TransactionManager transactionManager) {
        this.dataSource = dataSource;
        this.transactionManager = transactionManager;
    }

    @Override
    public Transaction createTransaction() {
        return new DataSourceTransaction(dataSource, transactionManager);
    }

    public DataSourceProxy createProxy() {
        return new DataSourceProxy(dataSource, transactionManager);
    }
}
