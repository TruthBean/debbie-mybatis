package com.truthbean.debbie.mybatis.annotation;

import com.truthbean.debbie.bean.BeanFactoryHandler;
import com.truthbean.debbie.bean.BeanInitialization;
import com.truthbean.debbie.bean.DebbieBeanInfo;
import com.truthbean.debbie.jdbc.datasource.DataSourceFactoryBeanRegister;
import com.truthbean.debbie.mybatis.DebbieMapperFactory;
import com.truthbean.debbie.mybatis.SqlSessionFactoryHandler;
import com.truthbean.debbie.properties.DebbieConfigurationFactory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.Set;

/**
 * @author TruthBean
 * @since 0.0.2
 * Created on 2019/06/02 18:27.
 */
public class MappedBeanRegister extends DataSourceFactoryBeanRegister {

    private final SqlSessionFactoryHandler sqlSessionFactoryHandler;
    private final BeanInitialization beanInitialization;
    private final BeanFactoryHandler beanFactoryHandler;

    public MappedBeanRegister(DebbieConfigurationFactory configurationFactory, BeanFactoryHandler beanFactoryHandler) {
        super(configurationFactory, beanFactoryHandler);
        this.beanFactoryHandler = beanFactoryHandler;
        sqlSessionFactoryHandler = new SqlSessionFactoryHandler(configurationFactory, beanFactoryHandler);
        beanInitialization = beanFactoryHandler.getBeanInitialization();
    }

    public void registerMapper() {
        Set<DebbieBeanInfo<?>> annotatedClass = beanInitialization.getAnnotatedClass(Mapper.class);
        if (annotatedClass != null && !annotatedClass.isEmpty()) {
            for (DebbieBeanInfo<?> mapperBean : annotatedClass) {
                DebbieMapperFactory mapperFactory = new DebbieMapperFactory<>(mapperBean.getBeanClass(), sqlSessionFactoryHandler);
                mapperFactory.setBeanFactoryHandler(beanFactoryHandler);
                mapperBean.setBeanFactory(mapperFactory);
                beanInitialization.refreshBean(mapperBean);
                beanFactoryHandler.refreshBeans();
            }
        }
    }

    /*public void register() {
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
                            beanInfo.setBeanFactory(new DebbieMapperFactory<>(targetClass, sqlSessionFactoryHandler));
                            beanInitialization.init(beanInfo);
                            beanFactoryHandler.refreshBeans();
                            register = true;
                            break;
                        } else if (type == MappedJdbcTypes.class || type == MappedTypes.class) {
                            DebbieBeanInfo beanInfo = new DebbieBeanInfo<>(targetClass);
                            beanInitialization.init(beanInfo);
                            beanFactoryHandler.refreshBeans();
                            register = true;
                            break;
                        }
                    }
                    if (!register) {
                        beanInitialization.init(targetClass);
                        beanFactoryHandler.refreshBeans();
                    }
                }
            }
        }
    }*/

    public void registerSqlSessionFactory() {
        registerSingletonBean(sqlSessionFactoryHandler.buildSqlSessionFactory(), SqlSessionFactory.class, "sqlSessionFactory");
    }
}
