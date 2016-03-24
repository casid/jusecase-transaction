package org.jusecase.transaction.simple.jdbc;

import org.junit.Before;
import org.junit.Test;
import org.jusecase.transaction.TransactionError;
import org.jusecase.transaction.simple.SimpleTransactionRunner;
import org.jusecase.transaction.simple.ThreadLocalTransactionManager;
import org.jusecase.transaction.simple.TransactionExecutionError;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Mockito.*;

public class SimpleTransactionRunnerTest {
    private SimpleTransactionRunner transactionRunner;

    private ThreadLocalTransactionManager transactionManager = new ThreadLocalTransactionManager();
    private DataSource dataSource;
    private Connection connection;

    private Throwable error;

    @Before
    public void setUp() throws Exception {
        dataSource = mock(DataSource.class);
        connection = mock(Connection.class);
        when(dataSource.getConnection()).thenReturn(connection);

        transactionRunner = new SimpleTransactionRunner(transactionManager);
        transactionRunner.setTransactionFactory(new DataSourceTransactionFactory(dataSource, transactionManager));
    }

    @Test
    public void openConnectionError() throws Exception {
        doThrow(new SQLException()).when(dataSource).getConnection();
        whenTransactionIsExecuted();
        thenTransactionInitializationFails();
    }

    @Test
    public void transactionInitializationError() throws Exception {
        doThrow(new SQLException()).when(connection).setAutoCommit(anyBoolean());

        whenTransactionIsExecuted();

        thenTransactionInitializationFails();
        thenTransactionIsClosed();
    }

    @Test
    public void commitError() throws Exception {
        doThrow(new SQLException()).when(connection).commit();

        whenTransactionIsExecuted();

        thenTransactionFailsWithErrorMessage("Failed to commit transaction.");
        thenTransactionIsRolledBack();
        thenTransactionIsClosed();
    }

    @Test
    public void rollbackError() throws Exception {
        doThrow(new SQLException()).when(connection).rollback();

        whenTransactionIsExecuted(new Runnable() {
            @Override
            public void run() {
                throw new RuntimeException("Must rollback!");
            }
        });

        thenTransactionFailsWithErrorMessage("Failed to rollback transaction.");
        thenTransactionIsClosed();
    }

    @Test
    public void connectionAutoCommitModeIsReset() throws SQLException {
        when(connection.getAutoCommit()).thenReturn(true);

        whenTransactionIsExecuted();

        verify(connection).setAutoCommit(false);
        verify(connection).setAutoCommit(true);
    }

    @Test
    public void maxTransactionAttempts_initialValue() {
        assertEquals(5, transactionRunner.getMaxTransactionAttempts());
    }

    @Test
    public void maxTransactionAttempts_areConsidered() {
        transactionRunner.setMaxTransactionAttempts(3);
        LockedTransaction lockedTransaction = new LockedTransaction();

        whenTransactionIsExecuted(lockedTransaction);

        assertEquals(3, lockedTransaction.getAttempts());
    }

    private void whenTransactionIsExecuted() {
        whenTransactionIsExecuted(new Runnable() {
            @Override
            public void run() {
            }
        });
    }

    private void whenTransactionIsExecuted(Runnable runnable) {
        try {
            transactionRunner.runAsTransaction(runnable);
        } catch (Throwable error) {
            this.error = error;
        }
    }

    private void thenTransactionFailsWithErrorMessage(String expected) {
        assertTrue(error instanceof TransactionError);
        assertEquals(expected, error.getMessage());
        assertNull(transactionManager.getCurrent());
    }

    private void thenTransactionInitializationFails() {
        thenTransactionFailsWithErrorMessage("Failed to create transaction.");
    }

    private void thenTransactionIsClosed() throws Exception {
        verify(connection, times(1)).close();
    }

    private void thenTransactionIsRolledBack() throws SQLException {
        verify(connection, times(1)).rollback();
    }

    private static class LockedTransaction implements Runnable {
        private int attempts;

        @Override
        public void run() {
            ++attempts;
            throw new LockedTransactionError();
        }

        public int getAttempts() {
            return attempts;
        }
    }

    private static class LockedTransactionError extends RuntimeException implements TransactionExecutionError {
        @Override
        public boolean shouldRetryTransaction() {
            return true;
        }
    }
}