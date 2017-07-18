package org.jusecase.transaction.simple;

import org.jusecase.transaction.Transaction;

public interface TransactionFactory {
    Transaction createTransaction();
}
