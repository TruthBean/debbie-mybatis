package com.truthbean.debbie.mybatis.configuration;

import com.truthbean.debbie.core.bean.BeanFactory;
import com.truthbean.debbie.core.bean.BeanInitialization;
import com.truthbean.debbie.core.properties.BaseProperties;

/**
 * @author truthbean
 * @since 0.0.2
 */
public class MybatisProperties extends BaseProperties {

    //===========================================================================
    private static final String MYBATIS_CONFIG_XML_LOCATION = "debbie.mybatis.config-xml-location";
    private static final String MYBATIS_ENVIRONMENT= "debbie.mybatis.environment";
    private static final String MYBATIS_PROPERTIES = "debbie.mybatis.properties.";
    //===========================================================================

    private MybatisConfiguration configuration;
    private static final MybatisProperties INSTANCE = new MybatisProperties();

    public MybatisProperties() {
        BeanInitialization initialization = new BeanInitialization();
        initialization.init(MyBatisConfigurationSettings.class);
        configuration = new MybatisConfiguration();

        configuration.setMybatisConfigXmlLocation(getValue(MYBATIS_CONFIG_XML_LOCATION));
        configuration.setEnvironment(getStringValue(MYBATIS_ENVIRONMENT, "default"));

        MyBatisConfigurationSettings settings = BeanFactory.factory(MyBatisConfigurationSettings.class);
        configuration.setSettings(settings);

        configuration.setConfigurationProperties(getMatchedKey(MYBATIS_PROPERTIES));
    }

    public static MybatisConfiguration toConfiguration() {
        return INSTANCE.configuration;
    }

    public MybatisConfiguration loadConfiguration() {
        return configuration;
    }
}
