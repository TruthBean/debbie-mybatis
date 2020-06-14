package com.truthbean.debbie.mybatis.annotation;

import com.truthbean.debbie.bean.AnnotationRegister;
import com.truthbean.debbie.bean.BeanInitialization;
import org.apache.ibatis.type.MappedJdbcTypes;

/**
 * @author truthbean
 * @since 0.0.2
 */
public class MappedJdbcTypesRegister implements AnnotationRegister<MappedJdbcTypes> {
    private final BeanInitialization initialization;
    public MappedJdbcTypesRegister(BeanInitialization beanInitialization) {
        this.initialization = beanInitialization;
    }

    @Override
    public void register() {
        register(MappedJdbcTypes.class);
    }

    @Override
    public BeanInitialization getBeanInitialization() {
        return initialization;
    }
}
