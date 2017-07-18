package org.jusecase.transaction.simple;

import org.jusecase.transaction.Transaction;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ThreadLocalTransactionManager implements TransactionManager {
    private final ThreadLocal<Transaction> transactions;

    @Inject
    public ThreadLocalTransactionManager() {
        transactions = new ThreadLocal<Transaction>();
    }

    @Override
    public Transaction getCurrent() {
        return transactions.get();
    }

    @Override
    public void setCurrent(Transaction transaction) {
        transactions.set(transaction);
    }
}
