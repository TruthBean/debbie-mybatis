package com.truthbean.debbie.mybatis.transaction;

import com.truthbean.debbie.core.bean.BeanInitialization;
import com.truthbean.debbie.core.bean.DebbieBeanInfo;
import com.truthbean.debbie.core.proxy.MethodProxyHandler;
import com.truthbean.debbie.jdbc.annotation.JdbcTransactional;
import com.truthbean.debbie.jdbc.datasource.DataSourceFactory;
import com.truthbean.debbie.jdbc.transaction.MethodNoJdbcTransactionalException;
import com.truthbean.debbie.jdbc.transaction.TransactionManager;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @author truthbean
 * @since 0.0.2
 */
public class MybatisTransactionalHandler implements MethodProxyHandler<JdbcTransactional> {
    private final MybatisTransactionInfo transactionInfo = new MybatisTransactionInfo();

    private JdbcTransactional jdbcTransactional;

    private JdbcTransactional classJdbcTransactional;

    private int order;

    @Override
    public int getOrder() {
        return order;
    }

    @Override
    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public boolean exclusive() {
        return true;
    }

    @Override
    public void setMethodAnnotation(JdbcTransactional methodAnnotation) {
        this.jdbcTransactional = methodAnnotation;
    }

    @Override
    public void setClassAnnotation(JdbcTransactional classAnnotation) {
        this.classJdbcTransactional = classAnnotation;
    }

    @Override
    public void setMethod(Method method) {
        transactionInfo.setMethod(method);
    }

    @Override
    public void before() {
        LOGGER.debug("running before method (" + transactionInfo.getMethod() + ") invoke ..");
        BeanInitialization beanInitialization = new BeanInitialization();
        DebbieBeanInfo<SqlSessionFactory> sqlSessionFactoryBeanInfo = beanInitialization.getRegisteredBean(SqlSessionFactory.class);
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBeanInfo.getBean();
        DebbieBeanInfo<DataSourceFactory> dataSourceFactoryBeanInfo = beanInitialization.getRegisteredBean(DataSourceFactory.class);
        DataSourceFactory dataSourceFactory = dataSourceFactoryBeanInfo.getBean();
        transactionInfo.setConnection(dataSourceFactory.getConnection());

        if (jdbcTransactional == null && classJdbcTransactional == null) {
            throw new MethodNoJdbcTransactionalException();
        } else if (jdbcTransactional == null && !classJdbcTransactional.readonly()) {
            transactionInfo.setAutoCommit(false);
        } else if (jdbcTransactional != null && !jdbcTransactional.readonly()) {
            transactionInfo.setAutoCommit(false);
        } else {
            transactionInfo.setAutoCommit(true);
        }
        SqlSession session = sqlSessionFactory.openSession(transactionInfo.getConnection());
        transactionInfo.setSession(session);
        TransactionManager.offer(transactionInfo);
    }

    @Override
    public void after() {
        LOGGER.debug("running after method (" + transactionInfo.getMethod() + ") invoke ..");
        transactionInfo.commit();
    }

    @Override
    public void whenExceptionCatched(Throwable e) throws Throwable {
        LOGGER.debug("running when method (" + transactionInfo.getMethod() + ") invoke throw exception and catched ..");
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
            }
        }
        throw e;
    }

    @Override
    public void finallyRun() {
        LOGGER.debug("running when method (" + transactionInfo.getMethod() + ") invoke throw exception and run to finally ..");
        transactionInfo.close();
        TransactionManager.remove();
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(MybatisTransactionalHandler.class);
}
