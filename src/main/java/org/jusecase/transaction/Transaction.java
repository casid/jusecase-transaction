package org.jusecase.transaction;

public interface Transaction {
    void commit();
    void rollback();
}
