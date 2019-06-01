package com.truthbean.debbie.mybatis.transaction;

import java.sql.Connection;
import java.sql.SQLException;

import com.truthbean.debbie.jdbc.transaction.TransactionInfo;
import com.truthbean.debbie.jdbc.transaction.TransactionManager;
import org.apache.ibatis.transaction.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@code DebbieManagedTransaction} handles the lifecycle of a JDBC connection. It retrieves a connection from Debbie's
 * transaction manager and returns it back to it when it is no longer needed.
 * <p>
 * If Spring's transaction handling is active it will no-op all commit/rollback/close calls assuming that the Debbie
 * transaction manager will do the job.
 * <p>
 * If it is not it will behave like {@code JdbcTransaction}.
 *
 * @author Hunter Presnall
 * @author Eduardo Macarron
 */
public class DebbieManagedTransaction implements Transaction {

    private static final Logger LOGGER = LoggerFactory.getLogger(DebbieManagedTransaction.class);

    private TransactionInfo transactionInfo;

    public DebbieManagedTransaction(TransactionInfo transactionInfo) {
        this.transactionInfo = transactionInfo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Connection getConnection() throws SQLException {
        if (this.transactionInfo == null) {
            openConnection();
        }
        return this.transactionInfo.getConnection();
    }

    /**
     * Gets a connection from Spring transaction manager and discovers if this {@code Transaction} should manage
     * connection or let it to Spring.
     * <p>
     * It also reads autocommit setting because when using Spring Transaction MyBatis thinks that autocommit is always
     * false and will always call commit/rollback so we need to no-op that calls.
     */
    private void openConnection() throws SQLException {
        this.transactionInfo = TransactionManager.peek();
        LOGGER.debug("JDBC Connection [" + this.transactionInfo + "] will" + (this.transactionInfo.getConnection() != null ? " " : " not ") + "be managed by Debbie");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void commit() throws SQLException {
        this.transactionInfo.commit();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void rollback() throws SQLException {
        this.transactionInfo.rollback();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        transactionInfo.close();
        TransactionManager.remove();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getTimeout() {
        return null;
    }

}