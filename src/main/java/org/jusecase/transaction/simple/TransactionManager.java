package org.jusecase.transaction.simple;

public interface TransactionManager {
    Transaction getCurrent();
    void setCurrent(Transaction transaction);
}
