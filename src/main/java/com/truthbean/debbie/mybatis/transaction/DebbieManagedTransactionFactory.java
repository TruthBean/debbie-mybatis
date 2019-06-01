package com.truthbean.debbie.mybatis.transaction;

import com.truthbean.debbie.jdbc.transaction.TransactionInfo;
import com.truthbean.debbie.jdbc.transaction.TransactionManager;
import org.apache.ibatis.session.TransactionIsolationLevel;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.TransactionFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Creates a {@code SpringManagedTransaction}.
 *
 * @author Hunter Presnall
 */
public class DebbieManagedTransactionFactory implements TransactionFactory {

    /**
     * {@inheritDoc}
     */
    @Override
    public Transaction newTransaction(DataSource dataSource, TransactionIsolationLevel level, boolean autoCommit) {
        TransactionInfo transactionInfo = new TransactionInfo();
        try {
            transactionInfo.setConnection(dataSource.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (level != null) {
            transactionInfo.setTransactionIsolation(level.getLevel());
        }
        transactionInfo.setAutoCommit(autoCommit);
        TransactionManager.offer(transactionInfo);
        return new DebbieManagedTransaction(transactionInfo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Transaction newTransaction(Connection connection) {
        TransactionInfo transactionInfo = new TransactionInfo();
        transactionInfo.setConnection(connection);
        TransactionManager.offer(transactionInfo);
        return new DebbieManagedTransaction(transactionInfo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setProperties(Properties props) {
        // not needed in this version
    }

}