package com.truthbean.debbie.mybatis.annotation;

import com.truthbean.debbie.core.bean.AnnotationRegister;
import org.apache.ibatis.type.Alias;

/**
 * @author truthbean
 * @since 0.0.2
 */
public class AliasRegister implements AnnotationRegister<Alias> {
    @Override
    public void register() {
        register(Alias.class);
    }
}
