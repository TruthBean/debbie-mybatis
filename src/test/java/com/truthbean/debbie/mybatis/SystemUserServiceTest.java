package com.truthbean.debbie.mybatis;

import com.truthbean.debbie.bean.BeanInject;
import com.truthbean.debbie.test.DebbieApplicationExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author TruthBean/Rogar·Q
 * @since Created on 2020-06-24 16:49.
 */
@ExtendWith({DebbieApplicationExtension.class})
class SystemUserServiceTest {

    @BeanInject
    private SystemUserService systemUserService;

    @Test
    void selectAll() {
        List<SystemUser> systemUsers = systemUserService.selectAll();
        System.out.println(systemUsers);
    }
}