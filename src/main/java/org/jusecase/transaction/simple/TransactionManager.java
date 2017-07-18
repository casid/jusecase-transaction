package org.jusecase.transaction.simple;

import org.jusecase.transaction.Transaction;

public interface TransactionManager {
    Transaction getCurrent();
    void setCurrent(Transaction transaction);
}
