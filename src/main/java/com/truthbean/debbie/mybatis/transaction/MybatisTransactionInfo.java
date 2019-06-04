package com.truthbean.debbie.mybatis.transaction;

import com.truthbean.debbie.jdbc.transaction.TransactionInfo;
import org.apache.ibatis.session.SqlSession;

/**
 * @author truthbean
 * @since 0.0.2
 */
public class MybatisTransactionInfo extends TransactionInfo {

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
            session.commit();
        } else {
            super.commit();
        }
    }

    @Override
    public void rollback() {
        if (session != null) {
            session.rollback();
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
}
