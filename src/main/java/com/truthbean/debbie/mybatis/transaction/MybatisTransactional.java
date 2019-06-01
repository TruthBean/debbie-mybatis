package com.truthbean.debbie.mybatis.transaction;

import com.truthbean.debbie.core.proxy.MethodProxy;

import java.lang.annotation.*;

/**
 * @author truthbean
 * @since 0.0.2
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@MethodProxy(proxyHandler = MybatisTransactionalHandler.class)
public @interface MybatisTransactional {
    boolean readonly() default true;

    boolean forceCommit() default false;

    Class<? extends Throwable> rollbackFor() default Exception.class;
}
