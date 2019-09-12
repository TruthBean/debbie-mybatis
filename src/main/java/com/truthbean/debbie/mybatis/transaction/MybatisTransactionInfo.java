package com.truthbean.debbie.mybatis.transaction;

import com.truthbean.debbie.jdbc.transaction.TransactionInfo;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.transaction.Transaction;

import java.sql.SQLException;

/**
 * @author truthbean
 * @since 0.0.2
 */
public class MybatisTransactionInfo extends TransactionInfo implements Transaction {

    private SqlSession session;

    public SqlSession getSession() {
        return session;
    }

    public void setSession(SqlSession session) {
        this.session = session;
    }

    @Override
    public void commit() {
        if (session != null) {
            session.commit(true);
        } else {
            super.commit();
        }
    }

    @Override
    public void rollback() {
        if (session != null) {
            session.rollback(true);
        } else {
            super.rollback();
        }
    }

    @Override
    public void close() {
        if (session != null) {
            session.close();
        } else {
            super.close();
        }
    }

    @Override
    public Integer getTimeout() throws SQLException {
        return getConnection().getNetworkTimeout();
    }
}
