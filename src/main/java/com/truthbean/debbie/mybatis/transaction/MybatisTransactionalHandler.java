package com.truthbean.debbie.mybatis.transaction;

import com.truthbean.debbie.core.proxy.MethodProxyHandler;
import com.truthbean.debbie.jdbc.datasource.DataSourceFactory;
import com.truthbean.debbie.jdbc.transaction.MethodNoJdbcTransactionalException;
import com.truthbean.debbie.jdbc.transaction.TransactionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @author truthbean
 * @since 0.0.2
 */
public class MybatisTransactionalHandler implements MethodProxyHandler<MybatisTransactional> {
    private final MybatisTransactionInfo transactionInfo = new MybatisTransactionInfo();

    private MybatisTransactional jdbcTransactional;

    private MybatisTransactional classJdbcTransactional;

    @Override
    public void setMethodAnnotation(MybatisTransactional methodAnnotation) {
        this.jdbcTransactional = methodAnnotation;
    }

    @Override
    public void setClassAnnotation(MybatisTransactional classAnnotation) {
        this.classJdbcTransactional = classAnnotation;
    }

    @Override
    public void setMethod(Method method) {
        transactionInfo.setMethod(method);
    }

    @Override
    public void before() {
        LOGGER.debug("runing before method (" + transactionInfo.getMethod() + ") invoke ..");
        DataSourceFactory factory = DataSourceFactory.factory();
        transactionInfo.setConnection(factory.getConnection());
        if (jdbcTransactional == null && classJdbcTransactional == null) {
            throw new MethodNoJdbcTransactionalException();
        } else if (jdbcTransactional == null && !classJdbcTransactional.readonly()) {
            transactionInfo.setAutoCommit(false);
        } else if (jdbcTransactional != null && !jdbcTransactional.readonly()) {
            transactionInfo.setAutoCommit(false);
        } else {
            transactionInfo.setAutoCommit(true);
        }
        TransactionManager.offer(transactionInfo);
    }

    @Override
    public void after() {
        LOGGER.debug("runing after method (" + transactionInfo.getMethod() + ") invoke ..");
        transactionInfo.commit();
    }

    @Override
    public void whenExceptionCatched(Throwable e) throws Throwable {
        LOGGER.debug("runing when method (" + transactionInfo.getMethod() + ") invoke throw exception and catched ..");
        if (jdbcTransactional.forceCommit()) {
            LOGGER.debug("force commit ..");
            transactionInfo.commit();
        } else {
            if (jdbcTransactional.rollbackFor().isInstance(e)) {
                transactionInfo.rollback();
                LOGGER.debug("rollback ..");
            } else {
                LOGGER.debug("not rollback for this exception(" + e.getClass().getName() + "), it committed");
                transactionInfo.commit();
                TransactionManager.remove();
            }
        }
        throw e;
    }

    @Override
    public void finallyRun() {
        LOGGER.debug("runing when method (" + transactionInfo.getMethod() + ") invoke throw exception and run to finally ..");
        transactionInfo.close();
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(MybatisTransactionalHandler.class);
}
