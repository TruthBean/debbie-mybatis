package com.truthbean.debbie.mybatis;

import com.truthbean.debbie.bean.BeanFactoryHandler;
import com.truthbean.debbie.bean.BeanInitialization;
import com.truthbean.debbie.bean.DebbieBeanInfo;
import com.truthbean.debbie.jdbc.datasource.DataSourceFactory;
import com.truthbean.debbie.mybatis.configuration.MybatisConfiguration;
import com.truthbean.debbie.mybatis.configuration.MybatisProperties;
import com.truthbean.debbie.mybatis.transaction.DebbieManagedTransactionFactory;
import com.truthbean.debbie.properties.DebbieConfigurationFactory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.scripting.LanguageDriverRegistry;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.type.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author truthbean
 * @since 0.0.2
 */
public class SqlSessionFactoryHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(SqlSessionFactoryHandler.class);

    private MybatisConfiguration mybatisConfiguration;

    private Configuration configuration;
    private InputStream mybatisConfigXmlInputStream;

    private BeanFactoryHandler beanFactoryHandler;
    private BeanInitialization beanInitialization;

    public SqlSessionFactoryHandler(DebbieConfigurationFactory configurationFactory, BeanFactoryHandler beanFactoryHandler) {
        this.beanFactoryHandler = beanFactoryHandler;
        beanInitialization = beanFactoryHandler.getBeanInitialization();
        this.mybatisConfiguration = MybatisProperties.toConfiguration(beanFactoryHandler);
        if (getMybatisConfigXmlInputStream() == null) {
            buildConfiguration(configurationFactory);
        }
    }

    private SqlSessionFactory sqlSessionFactory;

    public void onApplicationEvent() {
        configuration.getMappedStatementNames();
    }

    private InputStream getMybatisConfigXmlInputStream() {
        if (mybatisConfigXmlInputStream == null) {
            String resource = mybatisConfiguration.getMybatisConfigXmlLocation();
            if (resource != null) {
                try {
                    mybatisConfigXmlInputStream = Resources.getResourceAsStream(resource);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return mybatisConfigXmlInputStream;
    }

    private void buildSqlSessionFactoryByXml() {
        if (mybatisConfigXmlInputStream != null && sqlSessionFactory == null) {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(mybatisConfigXmlInputStream);
        }
    }

    private DataSourceFactory getDataSourceFactoryOrInitIfNull(DebbieConfigurationFactory configurationFactory) {
        DataSourceFactory dataSourceFactory = beanInitialization.getRegisterBean(DataSourceFactory.class);
        if (dataSourceFactory == null) {
            dataSourceFactory = DataSourceFactory.factory(configurationFactory, beanFactoryHandler);
            DebbieBeanInfo beanInfo = new DebbieBeanInfo<>(DataSourceFactory.class);
            beanInfo.setBeanName("dataSourceFactory");
            beanInfo.setBean(dataSourceFactory);
            beanInitialization.initSingletonBean(beanInfo);
            beanFactoryHandler.refreshBeans();
        }
        return dataSourceFactory;
    }

    private void buildConfiguration(DebbieConfigurationFactory configurationFactory) {
        DataSourceFactory dataSourceFactory = getDataSourceFactoryOrInitIfNull(configurationFactory);
        DataSource dataSource = dataSourceFactory.getDataSource();
        TransactionFactory transactionFactory = new DebbieManagedTransactionFactory();
        Environment environment = new Environment(mybatisConfiguration.getEnvironment(), transactionFactory, dataSource);
        configuration = new Configuration(environment);
        mybatisConfiguration.getSettings().configTo(configuration);

        Map<String, String> configurationProperties = mybatisConfiguration.getConfigurationProperties();
        if (configurationProperties != null && !configurationProperties.isEmpty()) {
            configuration.getVariables().putAll(configurationProperties);
        }
        ObjectFactory objectFactory = mybatisConfiguration.getObjectFactory();
        if (objectFactory != null) {
            configuration.setObjectFactory(objectFactory);
        }

        ObjectWrapperFactory objectWrapperFactory = mybatisConfiguration.getObjectWrapperFactory();
        if (objectWrapperFactory != null) {
            configuration.setObjectWrapperFactory(objectWrapperFactory);
        }

        Interceptor[] plugins = mybatisConfiguration.getPlugins();
        if (plugins != null && plugins.length > 0) {
            for (Interceptor plugin : plugins) {
                configuration.addInterceptor(plugin);
            }
        }

        Set<DebbieBeanInfo> alias = beanInitialization.getAnnotatedClass(Alias.class);
        TypeAliasRegistry typeAliasRegistry = configuration.getTypeAliasRegistry();
        if (alias != null && !alias.isEmpty()) {
            for (DebbieBeanInfo typeAlias : alias) {
                var typeAliasClass = typeAlias.getBeanClass();
                typeAliasRegistry.registerAlias(typeAliasClass);
                LOGGER.debug("Registered type alias: '" + typeAliasClass + "'");
            }
        }


        TypeHandler<?>[] typeHandlers = mybatisConfiguration.getTypeHandlers();
        TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
        if (typeHandlers != null && typeHandlers.length > 0) {
            for (TypeHandler<?> typeHandler : typeHandlers) {
                typeHandlerRegistry.register(typeHandler);
            }
        }

        Set<DebbieBeanInfo> mapped = new HashSet<>();
        Set<DebbieBeanInfo> mappedTypes = beanInitialization.getAnnotatedClass(MappedTypes.class);
        if (mappedTypes != null && !mappedTypes.isEmpty()) {
            mapped.addAll(mappedTypes);
        }
        Set<DebbieBeanInfo> mappedJdbcTypes = beanInitialization.getAnnotatedClass(MappedJdbcTypes.class);
        if (mappedJdbcTypes != null && !mappedJdbcTypes.isEmpty()) {
            mapped.addAll(mappedJdbcTypes);
        }
        if (!mapped.isEmpty()) {
            for (DebbieBeanInfo mappedType : mapped) {
                var mappedTypeClass = mappedType.getBeanClass();
                typeHandlerRegistry.register(mappedTypeClass);
                LOGGER.debug("Registered type handlers: '" + mappedTypeClass + "'");
            }
        }

        LanguageDriver[] scriptingLanguageDrivers = mybatisConfiguration.getScriptingLanguageDrivers();
        LanguageDriverRegistry languageRegistry = configuration.getLanguageRegistry();
        if (scriptingLanguageDrivers != null && scriptingLanguageDrivers.length > 0) {
            for (LanguageDriver scriptingLanguageDriver : scriptingLanguageDrivers) {
                languageRegistry.register(scriptingLanguageDriver);
            }
        }

        Cache cache = mybatisConfiguration.getCache();
        if (cache != null) {
            configuration.addCache(cache);
        }

        Set<DebbieBeanInfo> mappers = beanInitialization.getAnnotatedClass(Mapper.class);
        if (mappers != null && !mappers.isEmpty()) {
            for (DebbieBeanInfo mapper : mappers) {
                Class<?> mapperClass = mapper.getBeanClass();
                configuration.addMapper(mapperClass);
                LOGGER.debug("Registered type mapper: '" + mapperClass + "'");
            }
        }
    }

    private void buildSqlSessionFactoryByJavaConfig() {
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
    }

    public SqlSessionFactory buildSqlSessionFactory() {
        buildSqlSessionFactoryByXml();
        if (sqlSessionFactory == null) {
            buildSqlSessionFactoryByJavaConfig();
        }
        return sqlSessionFactory;
    }

}
