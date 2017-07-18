package org.jusecase.transaction.extensions;

import org.junit.jupiter.api.extension.ExtendWith;
import org.jusecase.transaction.TransactionRunner;

@ExtendWith(RunAsTransactionAndRollback.class)
public interface RunAsTransactionAndRollbackTest {
    TransactionRunner getTransactionRunner();
}
