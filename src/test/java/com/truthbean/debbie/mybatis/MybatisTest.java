/**
 * Copyright (c) 2020 TruthBean(Rogar·Q)
 * Debbie is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 *         http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 */
package com.truthbean.debbie.mybatis;

import com.truthbean.debbie.bean.BeanFactoryHandler;
import com.truthbean.debbie.boot.DebbieApplicationFactory;
import com.truthbean.debbie.jdbc.datasource.DataSourceFactory;
import com.truthbean.debbie.properties.DebbieConfigurationFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

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
