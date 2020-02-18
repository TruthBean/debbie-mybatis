package com.truthbean.debbie.mybatis;

import com.truthbean.debbie.bean.BeanFactory;
import com.truthbean.debbie.mybatis.support.SqlSessionDebbieSupport;
import org.apache.ibatis.session.SqlSessionFactory;

/**
 * @author TruthBean
 * @since 0.0.2
 * Created on 2019/06/02 18:38.
 */
public class DebbieMapperFactory<Mapper> extends SqlSessionDebbieSupport implements BeanFactory<Mapper> {
    private Class<Mapper> mapperInterface;

    private SqlSessionFactory sqlSessionFactory;
    private SqlSessionFactoryHandler handler;

    public DebbieMapperFactory() {
    }

    public DebbieMapperFactory(Class<Mapper> mapperInterface, SqlSessionFactoryHandler handler) {
        this.mapperInterface = mapperInterface;
        this.handler = handler;

        setSqlSessionFactory();
    }

    public void setHandler(SqlSessionFactoryHandler handler) {
        this.handler = handler;
    }

    private void setSqlSessionFactory() {
        sqlSessionFactory = handler.buildSqlSessionFactory();
        setSqlSessionFactory(sqlSessionFactory);
    }


    @Override
    public Mapper getBean() {
        return getSqlSession().getMapper(mapperInterface);
    }

    @Override
    public Class<Mapper> getBeanType() {
        return mapperInterface;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void destroy() {
        // do nothing
    }
}
