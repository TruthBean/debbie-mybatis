package com.truthbean.debbie.mybatis;

import com.truthbean.debbie.bean.BeanFactoryHandler;
import com.truthbean.debbie.bean.BeanInitialization;
import com.truthbean.debbie.bean.BeanScanConfiguration;
import com.truthbean.debbie.boot.DebbieApplicationFactory;
import com.truthbean.debbie.jdbc.datasource.DataSourceFactory;
import com.truthbean.debbie.properties.ClassesScanProperties;
import com.truthbean.debbie.properties.DebbieConfigurationFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDateTime;

public class MybatisTest {

    private static BeanFactoryHandler beanFactoryHandler;
    private static DebbieConfigurationFactory configurationFactory;

    @BeforeAll
    static void before() {
        DebbieApplicationFactory beanFactoryHandler = DebbieApplicationFactory.configure(MybatisTest.class);
        DataSourceFactory dataSourceFactory = beanFactoryHandler.factory("dataSourceFactory");
        configurationFactory = beanFactoryHandler.getConfigurationFactory();
        MybatisTest.beanFactoryHandler = beanFactoryHandler;
    }

    @Test
    public void testSqlSessionFactory() throws IOException {
        SqlSessionFactoryHandler handler = new SqlSessionFactoryHandler(configurationFactory, beanFactoryHandler);
        SqlSessionFactory sqlSessionFactory = handler.buildSqlSessionFactory();

        System.out.println(sqlSessionFactory);
    }

    @Test
    public void testSelectOneSurname() throws IOException {
        SqlSessionFactoryHandler handler = new SqlSessionFactoryHandler(configurationFactory, beanFactoryHandler);
        SqlSessionFactory sqlSessionFactory = handler.buildSqlSessionFactory();

        try (SqlSession session = sqlSessionFactory.openSession()) {
            SurnameMapper mapper = session.getMapper(SurnameMapper.class);
            Surname surname = mapper.selectOne(1L);
            System.out.println(surname);
        }
    }

    @Test
    public void testDataTimeMapper() throws IOException {
        SqlSessionFactoryHandler handler = new SqlSessionFactoryHandler(configurationFactory, beanFactoryHandler);
        SqlSessionFactory sqlSessionFactory = handler.buildSqlSessionFactory();

        try (SqlSession session = sqlSessionFactory.openSession()) {
            DateTimeMapper mapper = session.getMapper(DateTimeMapper.class);
            LocalDateTime localDateTime = mapper.now();
            System.out.println(localDateTime);
        }
    }

}
