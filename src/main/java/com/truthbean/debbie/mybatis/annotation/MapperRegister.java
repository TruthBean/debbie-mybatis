package com.truthbean.debbie.mybatis.annotation;

import com.truthbean.debbie.bean.AnnotationRegister;
import com.truthbean.debbie.bean.BeanInitialization;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author truthbean
 * @since 0.0.2
 */
public class MapperRegister implements AnnotationRegister<Mapper> {
    private final BeanInitialization initialization;

    public MapperRegister(BeanInitialization beanInitialization) {
        this.initialization = beanInitialization;
    }

    @Override
    public void register() {
        register(Mapper.class);
    }

    @Override
    public BeanInitialization getBeanInitialization() {
        return initialization;
    }
}
