package com.truthbean.debbie.mybatis.transaction;

import org.apache.ibatis.session.TransactionIsolationLevel;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.TransactionFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Creates {@code MybatisTransactionInfo} instances
 *
 * @author truthbean
 * @since 0.0.2
 */
public class DebbieManagedTransactionFactory implements TransactionFactory {

    /**
     * {@inheritDoc}
     */
    @Override
    public Transaction newTransaction(DataSource dataSource, TransactionIsolationLevel level, boolean autoCommit) {
        MybatisTransactionInfo transactionInfo = new MybatisTransactionInfo();
        try {
            transactionInfo.setConnection(dataSource.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (level != null) {
            transactionInfo.setTransactionIsolation(level.getLevel());
        }
        transactionInfo.setAutoCommit(autoCommit);
        return transactionInfo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Transaction newTransaction(Connection connection) {
        MybatisTransactionInfo transactionInfo = new MybatisTransactionInfo();
        transactionInfo.setConnection(connection);
        return transactionInfo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setProperties(Properties props) {
        // not needed in this version
    }

}