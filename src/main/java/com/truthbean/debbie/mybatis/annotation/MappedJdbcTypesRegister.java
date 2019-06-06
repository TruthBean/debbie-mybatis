package com.truthbean.debbie.mybatis.annotation;

import com.truthbean.debbie.bean.AnnotationRegister;
import org.apache.ibatis.type.MappedJdbcTypes;

/**
 * @author truthbean
 * @since 0.0.2
 */
public class MappedJdbcTypesRegister implements AnnotationRegister<MappedJdbcTypes> {
    @Override
    public void register() {
        register(MappedJdbcTypes.class);
    }
}
