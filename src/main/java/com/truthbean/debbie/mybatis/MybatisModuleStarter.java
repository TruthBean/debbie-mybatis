package com.truthbean.debbie.mybatis;

import com.truthbean.debbie.bean.BeanFactoryHandler;
import com.truthbean.debbie.boot.DebbieModuleStarter;
import com.truthbean.debbie.mybatis.annotation.MappedBeanRegister;
import com.truthbean.debbie.properties.DebbieConfigurationFactory;

/**
 * @author truthbean
 * @since 0.0.2
 */
public class MybatisModuleStarter implements DebbieModuleStarter {
    @Override
    public void starter(DebbieConfigurationFactory configurationFactory, BeanFactoryHandler beanFactoryHandler) {
        MappedBeanRegister register = new MappedBeanRegister(configurationFactory, beanFactoryHandler);
        register.registerMapper();
        register.registerSqlSessionFactory();
    }
}
