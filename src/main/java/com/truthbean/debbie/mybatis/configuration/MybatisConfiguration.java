package com.truthbean.debbie.mybatis.configuration;

import com.truthbean.debbie.properties.DebbieConfiguration;
import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.type.TypeHandler;

import java.util.Map;

/**
 * @author truthbean
 * @since 0.0.2
 */
public class MybatisConfiguration implements DebbieConfiguration {
    private String mybatisConfigXmlLocation;

    private Map<String, String> configurationProperties;

    private String environment;

    private MyBatisConfigurationSettings settings;

    private ObjectFactory objectFactory;
    private ObjectWrapperFactory objectWrapperFactory;

    private Interceptor[] plugins;
    private TypeHandler<?>[] typeHandlers;
    private LanguageDriver[] scriptingLanguageDrivers;
    private DatabaseIdProvider databaseIdProvider;
    private Cache cache;

    public String getMybatisConfigXmlLocation() {
        return mybatisConfigXmlLocation;
    }

    public void setMybatisConfigXmlLocation(String mybatisConfigXmlLocation) {
        this.mybatisConfigXmlLocation = mybatisConfigXmlLocation;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public MyBatisConfigurationSettings getSettings() {
        return settings;
    }

    public void setSettings(MyBatisConfigurationSettings settings) {
        this.settings = settings;
    }

    public Map<String, String> getConfigurationProperties() {
        return configurationProperties;
    }

    public void setConfigurationProperties(Map<String, String> configurationProperties) {
        this.configurationProperties = configurationProperties;
    }

    public ObjectFactory getObjectFactory() {
        return objectFactory;
    }

    public void setObjectFactory(ObjectFactory objectFactory) {
        this.objectFactory = objectFactory;
    }

    public ObjectWrapperFactory getObjectWrapperFactory() {
        return objectWrapperFactory;
    }

    public void setObjectWrapperFactory(ObjectWrapperFactory objectWrapperFactory) {
        this.objectWrapperFactory = objectWrapperFactory;
    }

    public Interceptor[] getPlugins() {
        return plugins;
    }

    public void setPlugins(Interceptor[] plugins) {
        this.plugins = plugins;
    }

    public TypeHandler<?>[] getTypeHandlers() {
        return typeHandlers;
    }

    public void setTypeHandlers(TypeHandler<?>[] typeHandlers) {
        this.typeHandlers = typeHandlers;
    }

    public LanguageDriver[] getScriptingLanguageDrivers() {
        return scriptingLanguageDrivers;
    }

    public void setScriptingLanguageDrivers(LanguageDriver[] scriptingLanguageDrivers) {
        this.scriptingLanguageDrivers = scriptingLanguageDrivers;
    }

    public DatabaseIdProvider getDatabaseIdProvider() {
        return databaseIdProvider;
    }

    public void setDatabaseIdProvider(DatabaseIdProvider databaseIdProvider) {
        this.databaseIdProvider = databaseIdProvider;
    }

    public Cache getCache() {
        return cache;
    }

    public void setCache(Cache cache) {
        this.cache = cache;
    }
}
