package org.jusecase.transaction;

public interface TransactionRunner {
    void runAsTransaction(Runnable task);
    Transaction startTransaction();
}
