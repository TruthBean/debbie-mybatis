package com.truthbean.debbie.mybatis.configuration;

import com.truthbean.debbie.bean.BeanFactoryHandler;
import com.truthbean.debbie.io.ResourceResolver;
import com.truthbean.debbie.net.uri.AntPathMatcher;
import com.truthbean.debbie.properties.BaseProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @author truthbean
 * @since 0.0.2
 */
public class MybatisProperties extends BaseProperties {

    //===========================================================================
    private static final String MYBATIS_CONFIG_XML_LOCATION = "debbie.mybatis.config-xml-location";
    private static final String MYBATIS_ENVIRONMENT = "debbie.mybatis.environment";
    private static final String MYBATIS_PROPERTIES = "debbie.mybatis.properties.";
    private static final String MYBATIS_MAPPER_LOCATIONS = "debbie.mybatis.mapper-locations";
    //===========================================================================

    private final MybatisConfiguration configuration;
    private static MybatisProperties instance;

    public MybatisProperties(BeanFactoryHandler beanFactoryHandler) {
        configuration = new MybatisConfiguration();

        configuration.setMybatisConfigXmlLocation(getValue(MYBATIS_CONFIG_XML_LOCATION));
        configuration.setEnvironment(getStringValue(MYBATIS_ENVIRONMENT, "default"));

        MyBatisConfigurationSettings settings = beanFactoryHandler.factory(MyBatisConfigurationSettings.class);
        configuration.setSettings(settings);

        configuration.setConfigurationProperties(getMatchedKey(MYBATIS_PROPERTIES));

        List<String> list = getStringListValue(MYBATIS_MAPPER_LOCATIONS, ";");
        ResourceResolver resourceResolver = beanFactoryHandler.getResourceResolver();
        resolveMapperLocations(list, resourceResolver);
    }

    private void resolveMapperLocations(List<String> patternList, ResourceResolver resourceResolver) {
        List<String> list = new ArrayList<>();
        if (patternList != null && !patternList.isEmpty()) {
            for (String pattern : patternList) {
                list.addAll(resourceResolver.getMatchedResources(pattern));
            }
        }
        configuration.setMapperLocations(list);
    }

    public static MybatisConfiguration toConfiguration(BeanFactoryHandler beanFactoryHandler) {
        if (instance == null) {
            instance = new MybatisProperties(beanFactoryHandler);
        }
        return instance.configuration;
    }

    public MybatisConfiguration loadConfiguration() {
        return configuration;
    }
}
