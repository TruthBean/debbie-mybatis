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
}
