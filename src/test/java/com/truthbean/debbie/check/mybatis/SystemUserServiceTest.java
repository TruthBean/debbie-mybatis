package com.truthbean.debbie.check.mybatis;

import com.truthbean.debbie.bean.BeanInject;
import com.truthbean.debbie.test.annotation.DebbieApplicationTest;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author TruthBean/Rogar·Q
 * @since Created on 2020-06-24 16:49.
 */
@DebbieApplicationTest
class SystemUserServiceTest {

    @BeanInject
    private SystemUserService systemUserService;

    @Test
    void selectAll() {
        List<SystemUser> systemUsers = systemUserService.selectAll();
        System.out.println(systemUsers);
    }
}