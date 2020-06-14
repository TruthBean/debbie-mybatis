package com.truthbean.debbie.mybatis;

import com.truthbean.debbie.bean.BeanInject;
import com.truthbean.debbie.jdbc.datasource.DataSourceFactory;
import com.truthbean.debbie.jdbc.transaction.TransactionManager;
import com.truthbean.debbie.test.DebbieApplicationExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author TruthBean/Rogar·Q
 * @since Created on 2020-03-23 17:13
 */
@ExtendWith({DebbieApplicationExtension.class})
class SystemUserMapperTest {

    @BeanInject
    private SystemUserMapper systemUserMapper;

    @BeforeEach
    public void before(@BeanInject("dataSourceFactory") DataSourceFactory factory) {
        TransactionManager.offer(factory.getTransaction());
    }

    @Test
    void selectAll() {
        List<SystemUser> systemUsers = systemUserMapper.selectAll();
        System.out.println(systemUsers);
    }
}