package org.jusecase.transaction.simple;

public interface TransactionExecutionError {
    boolean shouldRetryTransaction();
}
