package com.truthbean.debbie.mybatis;

import com.truthbean.debbie.bean.BeanFactoryHandler;
import com.truthbean.debbie.bean.BeanInitialization;
import com.truthbean.debbie.bean.SingletonBeanRegister;
import com.truthbean.debbie.boot.DebbieModuleStarter;
import com.truthbean.debbie.mybatis.annotation.*;
import com.truthbean.debbie.mybatis.configuration.MyBatisConfigurationSettings;
import com.truthbean.debbie.mybatis.configuration.transformer.*;
import com.truthbean.debbie.properties.DebbieConfigurationFactory;
import org.apache.ibatis.session.AutoMappingBehavior;
import org.apache.ibatis.session.AutoMappingUnknownColumnBehavior;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.LocalCacheScope;
import org.apache.ibatis.type.JdbcType;

/**
 * @author truthbean
 * @since 0.0.2
 */
public class MybatisModuleStarter implements DebbieModuleStarter {
    @Override
    public void registerBean(BeanFactoryHandler beanFactoryHandler) {
        BeanInitialization beanInitialization = beanFactoryHandler.getBeanInitialization();

        beanInitialization.init(MyBatisConfigurationSettings.class);

        registerTransformer(beanInitialization);

        beanInitialization.addAnnotationRegister(new MapperRegister());
        beanInitialization.addAnnotationRegister(new AliasRegister());
        beanInitialization.addAnnotationRegister(new MappedJdbcTypesRegister());
        beanInitialization.addAnnotationRegister(new MappedTypesRegister());
    }

    private void registerTransformer(BeanInitialization beanInitialization) {
        beanInitialization.registerDataTransformer(new AutoMappingBehaviorTransformer(), AutoMappingBehavior.class, String.class);
        beanInitialization.registerDataTransformer(new AutoMappingUnknownColumnBehaviorTransformer(), AutoMappingUnknownColumnBehavior.class, String.class);
        beanInitialization.registerDataTransformer(new ExecutorTypeTransformer(), ExecutorType.class, String.class);
        beanInitialization.registerDataTransformer(new JdbcTypeTransformer(), JdbcType.class, String.class);
        beanInitialization.registerDataTransformer(new LocalCacheScopeTransformer(), LocalCacheScope.class, String.class);
    }

    @Override
    public void starter(DebbieConfigurationFactory configurationFactory, BeanFactoryHandler beanFactoryHandler) {
        MappedBeanRegister register = new MappedBeanRegister(configurationFactory, beanFactoryHandler);
        register.registerMapper();
        register.registerSqlSessionFactory();
    }
}
