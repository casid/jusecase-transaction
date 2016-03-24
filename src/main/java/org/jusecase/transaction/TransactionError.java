package org.jusecase.transaction;

public class TransactionError extends RuntimeException {
    public TransactionError(String message) {
        super(message);
    }

    public TransactionError(String message, Throwable cause) {
        super(message, cause);
    }
}
