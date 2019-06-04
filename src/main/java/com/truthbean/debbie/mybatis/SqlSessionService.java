package com.truthbean.debbie.mybatis;

import com.truthbean.debbie.jdbc.transaction.TransactionInfo;
import com.truthbean.debbie.jdbc.transaction.TransactionManager;
import com.truthbean.debbie.mybatis.transaction.MybatisTransactionInfo;
import org.apache.ibatis.session.SqlSession;

public interface SqlSessionService {
    default TransactionInfo getTransaction() {
        return TransactionManager.peek();
    }

    default SqlSession getSqlSession() {
        MybatisTransactionInfo transactionInfo = (MybatisTransactionInfo) TransactionManager.peek();
        return transactionInfo.getSession();
    }
}
