package org.jusecase.transaction.simple;

public interface Transaction {
    void commit();
    void rollback();
}
