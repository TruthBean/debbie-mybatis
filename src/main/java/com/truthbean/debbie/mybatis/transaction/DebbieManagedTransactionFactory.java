package com.truthbean.debbie.mybatis.transaction;

import com.truthbean.debbie.jdbc.transaction.TransactionManager;
import org.apache.ibatis.session.TransactionIsolationLevel;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.TransactionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        LOGGER.debug("create MybatisTransactionInfo by dataSource ({}) with transactionIsolationLevel ({}) and autoCommit ({}) ",
                dataSource, level, autoCommit);
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
        LOGGER.debug("create MybatisTransactionInfo by connection {} ", connection);
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

    private static final Logger LOGGER = LoggerFactory.getLogger(DebbieManagedTransactionFactory.class);

}