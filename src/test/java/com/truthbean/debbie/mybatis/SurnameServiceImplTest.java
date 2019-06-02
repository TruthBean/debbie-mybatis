package com.truthbean.debbie.mybatis;

import com.truthbean.debbie.core.bean.BeanFactoryHandler;
import com.truthbean.debbie.core.bean.BeanInitialization;
import com.truthbean.debbie.mybatis.annotation.MappedBeanRegister;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
    private static BeanFactoryHandler beanFactoryHandler;

    static {
        BeanInitialization initialization = new BeanInitialization();
        initialization.init("com.truthbean.debbie.mybatis");

        beanFactoryHandler = new BeanFactoryHandler();

        MappedBeanRegister register = new MappedBeanRegister();
        register.register(beanFactoryHandler);
    }

    @BeforeAll
    static void setUp() {
        surnameService = beanFactoryHandler.factory("surnameService");
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
    }

    @Test
    void doNothing() {
    }
}