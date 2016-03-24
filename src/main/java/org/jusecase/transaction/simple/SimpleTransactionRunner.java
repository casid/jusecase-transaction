package org.jusecase.transaction.simple;

import org.jusecase.transaction.TransactionError;
import org.jusecase.transaction.TransactionRunner;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SimpleTransactionRunner implements TransactionRunner {
    private TransactionFactory transactionFactory;
    private final TransactionManager transactionManager;
    private int maxTransactionAttempts = 5;

    @Inject
    public SimpleTransactionRunner(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public void setTransactionFactory(TransactionFactory transactionFactory) {
        this.transactionFactory = transactionFactory;
    }

    public int getMaxTransactionAttempts() {
        return maxTransactionAttempts;
    }

    public void setMaxTransactionAttempts(int maxTransactionAttempts) {
        this.maxTransactionAttempts = maxTransactionAttempts;
    }

    public TransactionManager getTransactionManager() {
        return transactionManager;
    }

    @Override
    public void runAsTransaction(Runnable task) {
        runAsTransaction(task, maxTransactionAttempts - 1);
    }

    private void runAsTransaction(Runnable task, int attemptsLeft) {
        Transaction transaction = startTransaction();
        try {
            task.run();
            transaction.commit();
        } catch (RuntimeException error) {
            if (shouldRetryTransaction(error) && attemptsLeft > 0) {
                transaction.rollback();
                runAsTransaction(task, attemptsLeft - 1);
            } else {
                transaction.rollback();
                throw error;
            }
        }
    }

    private boolean shouldRetryTransaction(RuntimeException error) {
        return (error instanceof TransactionExecutionError) && ((TransactionExecutionError) error).shouldRetryTransaction();
    }

    private Transaction startTransaction() {
        if (transactionManager.getCurrent() != null) {
            throw new TransactionError("Nested transactions are not supported!");
        }

        Transaction transaction = transactionFactory.createTransaction();
        transactionManager.setCurrent(transaction);
        return transaction;
    }
}
