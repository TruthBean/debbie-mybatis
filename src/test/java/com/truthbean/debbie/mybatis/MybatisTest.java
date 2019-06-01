package com.truthbean.debbie.mybatis;

import com.truthbean.debbie.core.bean.BeanInitialization;
import com.truthbean.debbie.core.bean.DebbieBeanInfo;
import com.truthbean.debbie.jdbc.datasource.DataSourceFactory;
import com.truthbean.debbie.mybatis.configuration.MybatisConfiguration;
import com.truthbean.debbie.mybatis.configuration.MybatisProperties;
import com.truthbean.debbie.mybatis.transaction.DebbieManagedTransactionFactory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.type.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MybatisTest {

    private static MybatisConfiguration mybatisConfiguration;

    @BeforeAll
    static void before() {
        BeanInitialization initialization = new BeanInitialization();
        initialization.init("com.truthbean.debbie.mybatis");

        mybatisConfiguration = MybatisProperties.toConfiguration();
    }

    @Test
    public void testDebbieFramework() {
        DataSourceFactory dataSourceFactory = DataSourceFactory.factory();
        DataSource dataSource = dataSourceFactory.getDataSource();
        TransactionFactory transactionFactory = new DebbieManagedTransactionFactory();
        Environment environment = new Environment(mybatisConfiguration.getEnvironment(), transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        mybatisConfiguration.getSettings().configTo(configuration);

        Map<String, String> configurationProperties = mybatisConfiguration.getConfigurationProperties();
        if (configurationProperties != null && !configurationProperties.isEmpty()) {
            configuration.getVariables().putAll(configurationProperties);
        }
        ObjectFactory objectFactory = mybatisConfiguration.getObjectFactory();
        if (objectFactory != null) {
            configuration.setObjectFactory(objectFactory);
        }

        BeanInitialization initialization = new BeanInitialization();

        Set<DebbieBeanInfo> alias = initialization.getAnnotatedClass(Alias.class);
        TypeAliasRegistry typeAliasRegistry = configuration.getTypeAliasRegistry();
        if (alias != null && !alias.isEmpty()) {
            for (DebbieBeanInfo typeAlias : alias) {
                var typeAliasClass = typeAlias.getBeanClass();
                typeAliasRegistry.registerAlias(typeAliasClass);
                LOGGER.debug("Registered type alias: '" + typeAliasClass + "'");
            }
        }

        Set<DebbieBeanInfo> mapped = new HashSet<>();
        Set<DebbieBeanInfo> mappedTypes = initialization.getAnnotatedClass(MappedTypes.class);
        if (mappedTypes != null && !mappedTypes.isEmpty()) {
            mapped.addAll(mappedTypes);
        }
        Set<DebbieBeanInfo> mappedJdbcTypes = initialization.getAnnotatedClass(MappedJdbcTypes.class);
        if (mappedJdbcTypes != null && !mappedJdbcTypes.isEmpty()) {
            mapped.addAll(mappedJdbcTypes);
        }
        TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
        if (!mapped.isEmpty()) {
            for (DebbieBeanInfo mappedType : mapped) {
                var mappedTypeClass = mappedType.getBeanClass();
                typeHandlerRegistry.register(mappedTypeClass);
                LOGGER.debug("Registered type handlers: '" + mappedTypeClass + "'");
            }
        }

        Set<DebbieBeanInfo> mappers = initialization.getAnnotatedClass(Mapper.class);
        if (mappers != null && !mappers.isEmpty()) {
            for (DebbieBeanInfo mapper : mappers) {
                var mapperClass = mapper.getBeanClass();
                configuration.addMapper(mapperClass);
                LOGGER.debug("Registered type mapper: '" + mapperClass + "'");
            }
        }


        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);

        SqlSession session = sqlSessionFactory.openSession();
        try {
            DateTimeMapper mapper = session.getMapper(DateTimeMapper.class);
            LocalDateTime localDateTime = mapper.now();
            System.out.println(localDateTime);

        } finally {
            session.close();
        }
    }

    @Test
    public void testSqlSessionFactory() throws IOException {
        String resource = mybatisConfiguration.getMybatisConfigXmlLocation();

        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        System.out.println(sqlSessionFactory);
    }

    @Test
    public void testSelectOneSurname() throws IOException {
        String resource = mybatisConfiguration.getMybatisConfigXmlLocation();

        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        SqlSession session = sqlSessionFactory.openSession();
        try {
            SurnameMapper mapper = session.getMapper(SurnameMapper.class);
            Surname surname = mapper.selectOne(2L);
            System.out.println(surname);

        } finally {
            session.close();
        }
    }

    @Test
    public void testDataTimeMapper() throws IOException {
        String resource = mybatisConfiguration.getMybatisConfigXmlLocation();

        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        SqlSession session = sqlSessionFactory.openSession();
        try {
            DateTimeMapper mapper = session.getMapper(DateTimeMapper.class);
            LocalDateTime localDateTime = mapper.now();
            System.out.println(localDateTime);

        } finally {
            session.close();
        }
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(SqlSessionFactoryConfiguration.class);
}
