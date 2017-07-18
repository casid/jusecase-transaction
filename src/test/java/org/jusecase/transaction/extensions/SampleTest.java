package org.jusecase.transaction.extensions;

import org.junit.jupiter.api.*;
import org.jusecase.transaction.Transaction;
import org.jusecase.transaction.TransactionRunner;

public class SampleTest implements RunAsTransactionAndRollbackTest {

    private static int state;

    @Test
    void adjustState() {
        state = 10;
    }

    // @Test // Can't be green, enable to check that @AfterAll assertion holds
    void adjustStateAndThrow() {
        state = 10;
        throw new RuntimeException();
    }

    @AfterAll
    static void afterAll() {
        Assertions.assertEquals(0, state);
    }

    @Override
    public TransactionRunner getTransactionRunner() {
        return new TransactionRunner() {
            @Override
            public void runAsTransaction(Runnable task) {
                // Not needed
            }

            @Override
            public Transaction startTransaction() {
                return new Transaction() {
                    @Override
                    public void commit() {

                    }

                    @Override
                    public void rollback() {
                        state = 0;
                    }
                };
            }
        };
    }
}
