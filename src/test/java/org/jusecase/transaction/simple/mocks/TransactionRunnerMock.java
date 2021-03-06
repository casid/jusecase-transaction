package org.jusecase.transaction.simple.mocks;

import org.jusecase.transaction.Transaction;
import org.jusecase.transaction.TransactionRunner;
import org.jusecase.transaction.TransactionError;

public class TransactionRunnerMock implements TransactionRunner {
    private Object currentTask;

    @Override
    public void runAsTransaction(Runnable task) {
        beforeTask(task);
        task.run();
        afterTask();
    }

    @Override
    public Transaction startTransaction() {
        return new Transaction() {
            @Override
            public void commit() {
            }

            @Override
            public void rollback() {
            }
        };
    }

    protected void beforeTask(Object task) {
        if (currentTask != null) {
            throw new TransactionError("Nested transactions are not supported!");
        }

        currentTask = task;
    }

    protected void afterTask() {
        currentTask = null;
    }
}
