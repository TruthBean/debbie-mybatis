package com.truthbean.debbie.mybatis.annotation;

import com.truthbean.debbie.bean.AnnotationRegister;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author truthbean
 * @since 0.0.2
 */
public class MapperRegister implements AnnotationRegister<Mapper> {
    @Override
    public void register() {
        register(Mapper.class);
    }
}
