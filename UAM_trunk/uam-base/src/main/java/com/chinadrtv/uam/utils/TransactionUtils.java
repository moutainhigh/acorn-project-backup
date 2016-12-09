package com.chinadrtv.uam.utils;

import org.springframework.transaction.HeuristicCompletionException;
import org.springframework.transaction.IllegalTransactionStateException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * Utility for transaction
 * 
 * @author Qianyong,Deng
 * @since Oct 9, 2012
 * 
 */
public final class TransactionUtils {
	
	/**
	 * Default time out
	 */
	public final static int DEFAULT_TIMEOUT = 60;
	
	private final static String DEFAULT_TRANSACTION_MANAGE_BEAN_NAME = "transactionManager";

	/**
	 * Return transaction manager according to default transaction bean name
	 * {@link TransactionUtils#DEFAULT_TRANSACTION_MANAGE_BEAN_NAME}.
	 * 
	 * @param transactionBeanName
	 *            bean name of transaction
	 * @return transaction manager
	 */
	public static PlatformTransactionManager getTransactionManager() {
		return getTransactionManager(DEFAULT_TRANSACTION_MANAGE_BEAN_NAME);
	}
	
	/**
	 * Return transaction manager according to transaction bean name
	 * 
	 * @param transactionBeanName
	 *            bean name of transaction
	 * @return transaction manager
	 */
	public static PlatformTransactionManager getTransactionManager(
			String transactionBeanName) {
		PlatformTransactionManager transactionManager = ApplicationContextHelper
				.getBean(transactionBeanName);
		return transactionManager;
	}

	/**
	 * Return a currently active transaction or create a new one, according to
	 * the specified propagation behavior, default isolation level, not readOnly
	 * flag and default timeout.
	 * 
	 * @param propagation
	 *            propagation behavior
	 * @return transaction status object representing by propagation
	 */
	public static TransactionStatus beginTransaction(Propagation propagation) {
		return beginTransaction(propagation, Isolation.DEFAULT, false, DEFAULT_TIMEOUT);
	}
	
	/**
	 * Return a currently active transaction or create a new one, according to
	 * the specified propagation behavior, default isolation level, not readOnly
	 * flag and default timeout.
	 * 
	 * @param transactionBeanName
	 *            bean name of transaction
	 * @param propagation
	 *            propagation behavior
	 * @return transaction status object representing by propagation
	 */
	public static TransactionStatus beginTransaction(
			String transactionBeanName, Propagation propagation) {
		return beginTransaction(transactionBeanName, propagation,
				Isolation.DEFAULT, false, DEFAULT_TIMEOUT);
	}

	/**
	 * Return a currently active transaction or create a new one, according to
	 * the specified propagation behavior, default isolation level, not readOnly
	 * flag and timeout.
	 * 
	 * @param propagation
	 *            propagation behavior
	 * @param timeout
	 *            timeout for transaction
	 * @return transaction status object representing by propagation
	 */
	public static TransactionStatus beginTransaction(Propagation propagation, int timeout) {
		return beginTransaction(propagation, Isolation.DEFAULT, false, timeout);
	}
	
	/**
	 * Return a currently active transaction or create a new one, according to
	 * the specified propagation behavior, default isolation level, not readOnly
	 * flag and timeout.
	 * 
	 * @param transactionBeanName
	 *            bean name of transaction
	 * @param propagation
	 *            propagation behavior
	 * @param timeout
	 *            timeout for transaction
	 * @return transaction status object representing by propagation
	 */
	public static TransactionStatus beginTransaction(
			String transactionBeanName, Propagation propagation, int timeout) {
		return beginTransaction(transactionBeanName, propagation,
				Isolation.DEFAULT, false, timeout);
	}

	/**
	 * Return a currently active transaction or create a new one, according to
	 * the specified propagation behavior, default isolation level, readOnly flag and
	 * timeout.
	 * 
	 * @param propagation
	 *            propagation behavior
	 * @param readOnly
	 *            read-only flag
	 * @param timeout
	 *            timeout for transaction
	 * @return transaction status object representing by propagation
	 */
	public static TransactionStatus beginTransaction(Propagation propagation,
			boolean readOnly, int timeout) {
		return beginTransaction(propagation, Isolation.DEFAULT, readOnly, timeout);
	}
	
	/**
	 * Return a currently active transaction or create a new one, according to
	 * the specified propagation behavior, default isolation level, readOnly
	 * flag and timeout.
	 * 
	 * @param transactionBeanName
	 *            bean name of transaction
	 * @param propagation
	 *            propagation behavior
	 * @param readOnly
	 *            read-only flag
	 * @param timeout
	 *            timeout for transaction
	 * @return transaction status object representing by propagation
	 */
	public static TransactionStatus beginTransaction(
			String transactionBeanName, Propagation propagation,
			boolean readOnly, int timeout) {
		return beginTransaction(transactionBeanName, propagation,
				Isolation.DEFAULT, readOnly, timeout);
	}

	/**
	 * Return a currently active transaction or create a new one, according to
	 * the specified propagation behavior, isolation level, readOnly flag and
	 * timeout.
	 * 
	 * @param propagation
	 *            propagation behavior
	 * @param isolation
	 *            isolation level
	 * @param readOnly
	 *            read-only flag
	 * @param timeout
	 *            timeout for transaction
	 * @return transaction status object representing by propagation
	 */
	public static TransactionStatus beginTransaction(Propagation propagation,
			Isolation isolation, boolean readOnly, int timeout) {
		DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
		definition.setPropagationBehavior(propagation.value());
		definition.setIsolationLevel(isolation.value());
		definition.setReadOnly(readOnly);
		definition.setTimeout(timeout);
		return getTransactionManager().getTransaction(definition);
	}
	
	/**
	 * Return a currently active transaction or create a new one, according to
	 * the specified propagation behavior, isolation level, readOnly flag and
	 * timeout.
	 * 
	 * @param transactionBeanName
	 *            bean name of transaction
	 * @param propagation
	 *            propagation behavior
	 * @param isolation
	 *            isolation level
	 * @param readOnly
	 *            read-only flag
	 * @param timeout
	 *            timeout for transaction
	 * @return transaction status object representing by propagation
	 */
	public static TransactionStatus beginTransaction(
			String transactionBeanName, Propagation propagation,
			Isolation isolation, boolean readOnly, int timeout) {
		DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
		definition.setPropagationBehavior(propagation.value());
		definition.setIsolationLevel(isolation.value());
		definition.setReadOnly(readOnly);
		definition.setTimeout(timeout);
		return getTransactionManager(transactionBeanName).getTransaction(
				definition);
	}

	/**
	 * Return a currently active transaction or create a new one, according to
	 * the specified propagation behavior, default isolation level, not readOnly
	 * flag and default timeout.
	 * 
	 * @return transaction status object representing by {@link Propagation#REQUIRED}
	 */
	public static TransactionStatus required() {
		DefaultTransactionDefinition definition = new DefaultTransactionDefinition(
				Propagation.REQUIRED.value());
		definition.setTimeout(DEFAULT_TIMEOUT);
		return getTransactionManager().getTransaction(definition);
	}

	/**
	 * Return a currently active transaction or create a new one, according to
	 * the specified propagation behavior, default isolation level, not readOnly
	 * flag and default timeout.
	 * 
	 * @return transaction status object representing by {@link Propagation#REQUIRES_NEW}
	 */
	public static TransactionStatus requiresNew() {
		DefaultTransactionDefinition definition = new DefaultTransactionDefinition(
				Propagation.REQUIRES_NEW.value());
		definition.setTimeout(DEFAULT_TIMEOUT);
		return getTransactionManager().getTransaction(definition);
	}

	/**
	 * Return a currently active transaction or create a new one, according to
	 * the specified propagation behavior, default isolation level, readOnly
	 * flag and default timeout.
	 * 
	 * @return transaction status object representing by {@link Propagation#NOT_SUPPORTED}
	 */
	public static TransactionStatus readOnly() {
		DefaultTransactionDefinition definition = new DefaultTransactionDefinition(
				Propagation.NOT_SUPPORTED.value());
		definition.setTimeout(DEFAULT_TIMEOUT);
		definition.setReadOnly(true);
		return getTransactionManager().getTransaction(definition);
	}

	/**
	 * Commit the given transaction, with regard to its status. If the
	 * transaction has been marked rollback-only programmatically, perform a
	 * rollback.
	 * <p>
	 * If the transaction wasn't a new one, omit the commit for proper
	 * participation in the surrounding transaction. If a previous transaction
	 * has been suspended to be able to create a new one, resume the previous
	 * transaction after committing the new one.
	 * <p>
	 * Note that when the commit call completes, no matter if normally or
	 * throwing an exception, the transaction must be fully completed and
	 * cleaned up. No rollback call should be expected in such a case.
	 * <p>
	 * If this method throws an exception other than a TransactionException,
	 * then some before-commit error caused the commit attempt to fail. For
	 * example, an O/R Mapping tool might have tried to flush changes to the
	 * database right before commit, with the resulting DataAccessException
	 * causing the transaction to fail. The original exception will be
	 * propagated to the caller of this commit method in such a case.
	 * 
	 * @param status
	 *            object returned by the <code>getTransaction</code> method
	 * @throws UnexpectedRollbackException
	 *             in case of an unexpected rollback that the transaction
	 *             coordinator initiated
	 * @throws HeuristicCompletionException
	 *             in case of a transaction failure caused by a heuristic
	 *             decision on the side of the transaction coordinator
	 * @throws TransactionSystemException
	 *             in case of commit or system errors (typically caused by
	 *             fundamental resource failures)
	 * @throws IllegalTransactionStateException
	 *             if the given transaction is already completed (that is,
	 *             committed or rolled back)
	 * @see TransactionStatus#setRollbackOnly
	 */
	public static void commit(TransactionStatus status) {
		getTransactionManager().commit(status);
	}

	/**
	 * Perform a rollback of the given transaction.
	 * <p>
	 * If the transaction wasn't a new one, just set it rollback-only for proper
	 * participation in the surrounding transaction. If a previous transaction
	 * has been suspended to be able to create a new one, resume the previous
	 * transaction after rolling back the new one.
	 * <p>
	 * <b>Do not call rollback on a transaction if commit threw an
	 * exception.</b> The transaction will already have been completed and
	 * cleaned up when commit returns, even in case of a commit exception.
	 * Consequently, a rollback call after commit failure will lead to an
	 * IllegalTransactionStateException.
	 * 
	 * @param status
	 *            object returned by the <code>getTransaction</code> method
	 * @throws TransactionSystemException
	 *             in case of rollback or system errors (typically caused by
	 *             fundamental resource failures)
	 * @throws IllegalTransactionStateException
	 *             if the given transaction is already completed (that is,
	 *             committed or rolled back)
	 */
	public static void rollback(TransactionStatus status) {
		getTransactionManager().rollback(status);
	}

}
