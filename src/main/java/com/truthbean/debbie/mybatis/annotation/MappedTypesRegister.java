package com.truthbean.debbie.mybatis.annotation;

import com.truthbean.debbie.bean.AnnotationRegister;
import com.truthbean.debbie.bean.BeanInitialization;
import org.apache.ibatis.type.MappedTypes;

/**
 * @author truthbean
 * @since 0.0.2
 */
public class MappedTypesRegister implements AnnotationRegister<MappedTypes> {
    private final BeanInitialization initialization;
    public MappedTypesRegister(BeanInitialization beanInitialization) {
        this.initialization = beanInitialization;
    }

    @Override
    public void register() {
        register(MappedTypes.class);
    }

    @Override
    public BeanInitialization getBeanInitialization() {
        return initialization;
    }
}
