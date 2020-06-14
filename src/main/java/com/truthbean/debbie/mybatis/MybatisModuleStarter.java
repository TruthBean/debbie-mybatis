/**
 * Copyright (c) 2020 TruthBean(Rogar·Q)
 *    Debbie is licensed under Mulan PSL v2.
 *    You can use this software according to the terms and conditions of the Mulan PSL v2.
 *    You may obtain a copy of Mulan PSL v2 at:
 *                http://license.coscl.org.cn/MulanPSL2
 *    THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 *    See the Mulan PSL v2 for more details.
 */
package com.truthbean.debbie.mybatis;

import com.truthbean.debbie.bean.BeanFactoryHandler;
import com.truthbean.debbie.bean.BeanInitialization;
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
    public void registerBean(BeanFactoryHandler beanFactoryHandler, BeanInitialization beanInitialization) {
        beanInitialization.init(MyBatisConfigurationSettings.class);

        registerTransformer(beanInitialization);

        beanInitialization.addAnnotationRegister(new MapperRegister(beanInitialization));
        beanInitialization.addAnnotationRegister(new AliasRegister(beanInitialization));
        beanInitialization.addAnnotationRegister(new MappedJdbcTypesRegister(beanInitialization));
        beanInitialization.addAnnotationRegister(new MappedTypesRegister(beanInitialization));

        // MethodProxyHandlerRegister methodProxyHandlerRegister = beanFactoryHandler.getMethodProxyHandlerRegister();
        // methodProxyHandlerRegister.register(JdbcTransactional.class, MybatisTransactionalHandler.class);
    }

    private void registerTransformer(BeanInitialization beanInitialization) {
        beanInitialization.registerDataTransformer(new AutoMappingBehaviorTransformer(), AutoMappingBehavior.class, String.class);
        beanInitialization.registerDataTransformer(new AutoMappingUnknownColumnBehaviorTransformer(), AutoMappingUnknownColumnBehavior.class, String.class);
        beanInitialization.registerDataTransformer(new ExecutorTypeTransformer(), ExecutorType.class, String.class);
        beanInitialization.registerDataTransformer(new JdbcTypeTransformer(), JdbcType.class, String.class);
        beanInitialization.registerDataTransformer(new LocalCacheScopeTransformer(), LocalCacheScope.class, String.class);
    }

    @Override
    public void configure(DebbieConfigurationFactory configurationFactory, BeanFactoryHandler beanFactoryHandler) {
        MappedBeanRegister register = new MappedBeanRegister(configurationFactory, beanFactoryHandler);
        register.registerMapper();
        register.registerSqlSessionFactory();
    }

    @Override
    public int getOrder() {
        return 51;
    }

}
