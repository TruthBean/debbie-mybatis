package com.truthbean.debbie.mybatis.annotation;

import com.truthbean.debbie.bean.AnnotationRegister;
import com.truthbean.debbie.bean.BeanInitialization;
import org.apache.ibatis.type.Alias;

/**
 * @author truthbean
 * @since 0.0.2
 */
public class AliasRegister implements AnnotationRegister<Alias> {
    private final BeanInitialization initialization;
    public AliasRegister(BeanInitialization beanInitialization) {
        this.initialization = beanInitialization;
    }

    @Override
    public void register() {
        register(Alias.class);
    }

  @Override
  public BeanInitialization getBeanInitialization() {
    return initialization;
  }
}
