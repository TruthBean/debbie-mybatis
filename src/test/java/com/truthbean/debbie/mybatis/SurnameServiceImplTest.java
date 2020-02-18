package com.truthbean.debbie.mybatis;

import com.truthbean.debbie.boot.DebbieApplicationFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

/**
 * @author TruthBean
 * @since
 * Created on 2019/06/02 14:35.
 */
class SurnameServiceImplTest {

    private static SurnameService surnameService;
    private static DebbieApplicationFactory beanFactoryHandler;

    static {
        ClassLoader classLoader = SurnameServiceImplTest.class.getClassLoader();
        beanFactoryHandler = new DebbieApplicationFactory(classLoader);
        beanFactoryHandler.config();
        beanFactoryHandler.callStarter();
    }

    @BeforeAll
    static void setUp() {
        surnameService = beanFactoryHandler.factory("surnameService");
    }

    @AfterAll
    static void after() {
        beanFactoryHandler.release();
    }

    @Test
    void insert() {
        var q = new Surname();
        q.setBegin(new Timestamp(System.currentTimeMillis() - 24 * 60 * 60 * 1000));
        q.setOrigin("");
        q.setWebsite("https://www.zhou.org");
        q.setName("周");
        var b = surnameService.insert(q);
        System.out.println(b);
        System.out.println(q);

        List<Surname> surnames = surnameService.selectAll();
        System.out.println(surnames);
        System.out.println("-----------------------------------------------------");
        surnames = surnameService.selectAll();
        System.out.println(surnames);
    }

    @Test
    void save() throws MalformedURLException {
        var q = new Surname();
        q.setId(1L);
        q.setBegin(new Timestamp(System.currentTimeMillis()));
        q.setOrigin("1");
        q.setWebsite("https://www.zhu.org");
        q.setName("zhu");
        var b = surnameService.save(q);
        System.out.println(b);
        System.out.println(q);
    }

    @Test
    void selectById() {
        Optional<Surname> surname = surnameService.selectById(1L);
        System.out.println(surname);
    }

    @Test
    void selectAll() {
        List<Surname> surnames = surnameService.selectAll();
        System.out.println(surnames);
        System.out.println("-----------------------------------------------------");
        surnames = surnameService.selectAll();
        System.out.println(surnames);
    }

    @Test
    void doNothing() {
    }
}