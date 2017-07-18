package org.jusecase.transaction.extensions;

import org.junit.jupiter.api.extension.*;
import org.jusecase.transaction.Transaction;
import org.jusecase.transaction.TransactionRunner;

public class RunAsTransactionAndRollback implements BeforeEachCallback, AfterEachCallback, TestInstancePostProcessor {

    private static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create("org", "jusecase", "transaction");

    @Override
    public void postProcessTestInstance(Object o, ExtensionContext context) throws Exception {
        TransactionRunner transactionRunner = null;
        if (o instanceof RunAsTransactionAndRollbackTest){
            transactionRunner = ((RunAsTransactionAndRollbackTest) o).getTransactionRunner();
            context.getStore(NAMESPACE).put("transactionRunner", transactionRunner);
        }

        if (transactionRunner == null) {
            throw new RuntimeException("This extension needs a transaction runner to work!");
        }
    }

    @Override
    public void beforeEach(TestExtensionContext context) throws Exception {
        TransactionRunner transactionRunner = context.getStore(NAMESPACE).get("transactionRunner", TransactionRunner.class);
        Transaction transaction = transactionRunner.startTransaction();
        context.getStore(NAMESPACE).put("transaction", transaction);
    }

    @Override
    public void afterEach(TestExtensionContext context) throws Exception {
        Transaction transaction = context.getStore(NAMESPACE).remove("transaction", Transaction.class);
        transaction.rollback();
    }
}
