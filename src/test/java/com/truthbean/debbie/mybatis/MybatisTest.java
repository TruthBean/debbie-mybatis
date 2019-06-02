package com.truthbean.debbie.mybatis;

import com.truthbean.debbie.core.bean.BeanFactoryHandler;
import com.truthbean.debbie.core.bean.BeanInitialization;
import com.truthbean.debbie.core.bean.BeanScanConfiguration;
import com.truthbean.debbie.core.properties.ClassesScanProperties;
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

    @BeforeAll
    static void before() {
        BeanInitialization initialization = new BeanInitialization();
        BeanScanConfiguration configuration = ClassesScanProperties.toConfiguration();
        initialization.init(configuration.getTargetClasses());
        beanFactoryHandler = new BeanFactoryHandler();
    }

    @Test
    public void testSqlSessionFactory() throws IOException {
        SqlSessionFactoryHandler handler = new SqlSessionFactoryHandler(beanFactoryHandler);
        SqlSessionFactory sqlSessionFactory = handler.buildSqlSessionFactory();

        System.out.println(sqlSessionFactory);
    }

    @Test
    public void testSelectOneSurname() throws IOException {
        SqlSessionFactoryHandler handler = new SqlSessionFactoryHandler(beanFactoryHandler);
        SqlSessionFactory sqlSessionFactory = handler.buildSqlSessionFactory();

        SqlSession session = sqlSessionFactory.openSession();
        try {
            SurnameMapper mapper = session.getMapper(SurnameMapper.class);
            Surname surname = mapper.selectOne(1L);
            System.out.println(surname);

        } finally {
            session.close();
        }
    }

    @Test
    public void testDataTimeMapper() throws IOException {
        SqlSessionFactoryHandler handler = new SqlSessionFactoryHandler(beanFactoryHandler);
        SqlSessionFactory sqlSessionFactory = handler.buildSqlSessionFactory();

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
