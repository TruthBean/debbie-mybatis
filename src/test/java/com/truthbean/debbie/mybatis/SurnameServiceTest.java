package com.truthbean.debbie.mybatis;

import com.truthbean.debbie.bean.BeanInject;
import com.truthbean.debbie.test.DebbieApplicationExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.net.MalformedURLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@ExtendWith({DebbieApplicationExtension.class})
class SurnameServiceTest {

    @Test
    void insert(@BeanInject SurnameService surnameService) {
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
    void save(@BeanInject SurnameService surnameService) throws MalformedURLException {
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
    void selectById(@BeanInject SurnameService surnameService) {
        Optional<Surname> surname = surnameService.selectById(1L);
        System.out.println(surname);
    }

    @Test
    void selectAll(@BeanInject SurnameService surnameService) {
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