package com.truthbean.debbie.mybatis.annotation;

import com.truthbean.debbie.core.bean.AnnotationRegister;
import org.apache.ibatis.type.MappedTypes;

/**
 * @author truthbean
 * @since 0.0.2
 */
public class MappedTypesRegister implements AnnotationRegister<MappedTypes> {
    @Override
    public void register() {
        register(MappedTypes.class);
    }
}
