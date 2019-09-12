package com.truthbean.debbie.mybatis;

import com.truthbean.debbie.bean.BeanInject;
import com.truthbean.debbie.test.DebbieApplicationExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDateTime;

@ExtendWith({DebbieApplicationExtension.class})
public class MybatisBeanTest {

    @Test
    public void content() {
        System.out.println("nothing");
    }

    @Test
    public void testDataTimeMapper(@BeanInject DateTimeMapper mapper) {
        LocalDateTime localDateTime = mapper.now();
        System.out.println(localDateTime);
    }
}
