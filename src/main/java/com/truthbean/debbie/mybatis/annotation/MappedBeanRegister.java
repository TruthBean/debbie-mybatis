package com.truthbean.debbie.mybatis.annotation;

import com.truthbean.debbie.core.bean.BeanFactoryHandler;
import com.truthbean.debbie.core.bean.BeanInitialization;
import com.truthbean.debbie.core.bean.BeanScanConfiguration;
import com.truthbean.debbie.core.bean.DebbieBeanInfo;
import com.truthbean.debbie.core.properties.ClassesScanProperties;
import com.truthbean.debbie.jdbc.datasource.DataSourceFactoryBeanRegister;
import com.truthbean.debbie.mybatis.DebbieMapperFactory;
import com.truthbean.debbie.mybatis.SqlSessionFactoryHandler;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * @author TruthBean
 * @since 0.0.2
 * Created on 2019/06/02 18:27.
 */
public class MappedBeanRegister {

    public void register(BeanFactoryHandler beanFactoryHandler) {
        SqlSessionFactoryHandler handler = new SqlSessionFactoryHandler(beanFactoryHandler);

        BeanInitialization initialization = new BeanInitialization();
        BeanScanConfiguration configuration = ClassesScanProperties.toConfiguration();
        Set<Class<?>> targetClasses = configuration.getTargetClasses();
        if (targetClasses != null && !targetClasses.isEmpty()) {
            for (Class<?> targetClass : targetClasses) {
                Annotation[] annotations = targetClass.getAnnotations();
                if (annotations != null && annotations.length > 0) {
                    var register = false;
                    for (Annotation annotation : annotations) {
                        var type = annotation.annotationType();
                        if (type == Mapper.class) {
                            DebbieBeanInfo beanInfo = new DebbieBeanInfo<>(targetClass);
                            beanInfo.setBeanFactory(new DebbieMapperFactory<>(targetClass, handler));
                            initialization.init(beanInfo);
                            beanFactoryHandler.refreshBeans();
                            register = true;
                            break;
                        } else if (type == MappedJdbcTypes.class || type == MappedTypes.class) {
                            DebbieBeanInfo beanInfo = new DebbieBeanInfo<>(targetClass);
                            initialization.init(beanInfo);
                            beanFactoryHandler.refreshBeans();
                            register = true;
                            break;
                        }
                    }
                    if (!register) {
                        initialization.init(targetClass);
                        beanFactoryHandler.refreshBeans();
                    }
                }
            }
        }

        DebbieBeanInfo beanInfo = new DebbieBeanInfo<>(SqlSessionFactory.class);
        beanInfo.setBean(handler.buildSqlSessionFactory());
        initialization.init(beanInfo);
        DataSourceFactoryBeanRegister.register(beanFactoryHandler, initialization);
        beanFactoryHandler.refreshBeans();
    }
}
