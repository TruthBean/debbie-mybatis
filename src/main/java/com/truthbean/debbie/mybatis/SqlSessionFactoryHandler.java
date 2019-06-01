package com.truthbean.debbie.mybatis;

import com.truthbean.debbie.core.reflection.ReflectionHelper;
import com.truthbean.debbie.core.util.StringUtils;
import com.truthbean.debbie.mybatis.transaction.DebbieManagedTransactionFactory;
import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Modifier;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author truthbean
 * @since 0.0.2
 */
public class SqlSessionFactoryHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(SqlSessionFactoryHandler.class);

    private SqlSessionFactoryConfiguration factoryConfiguration;

    public SqlSessionFactoryHandler(SqlSessionFactoryConfiguration factoryConfiguration) {
        this.factoryConfiguration = factoryConfiguration;
    }

    /**
     * Build a {@code SqlSessionFactory} instance.
     *
     * The default implementation uses the standard MyBatis {@code XMLConfigBuilder} API to build a
     * {@code SqlSessionFactory} instance based on an Reader. Since 1.3.0, it can be specified a {@link Configuration}
     * instance directly(without config file).
     *
     * @return SqlSessionFactory
     * @throws Exception
     *           if configuration is failed
     */
    protected SqlSessionFactory buildSqlSessionFactory() throws Exception {

        final Configuration targetConfiguration;

        XMLConfigBuilder xmlConfigBuilder = null;
        if (factoryConfiguration.configuration != null) {
            targetConfiguration = factoryConfiguration.getConfiguration();
            if (targetConfiguration.getVariables() == null) {
                targetConfiguration.setVariables(factoryConfiguration.configurationProperties);
            } else if (factoryConfiguration.configurationProperties != null) {
                targetConfiguration.getVariables().putAll(factoryConfiguration.configurationProperties);
            }
        } else if (factoryConfiguration.configLocation != null) {
            InputStream inputStream = Resources.getResourceAsStream(factoryConfiguration.configLocation);
            xmlConfigBuilder = new XMLConfigBuilder(inputStream, null, factoryConfiguration.configurationProperties);
            targetConfiguration = xmlConfigBuilder.getConfiguration();
        } else {
            LOGGER.debug("Property 'configuration' or 'configLocation' not specified, using default MyBatis Configuration");
            targetConfiguration = new Configuration();
            Optional.ofNullable(factoryConfiguration.configurationProperties).ifPresent(targetConfiguration::setVariables);
        }

        Optional.ofNullable(factoryConfiguration.objectFactory).ifPresent(targetConfiguration::setObjectFactory);
        Optional.ofNullable(factoryConfiguration.objectWrapperFactory).ifPresent(targetConfiguration::setObjectWrapperFactory);
        Optional.ofNullable(factoryConfiguration.vfs).ifPresent(targetConfiguration::setVfsImpl);

        if (StringUtils.hasLength(factoryConfiguration.typeAliasesPackage)) {
            scanClasses(factoryConfiguration.typeAliasesPackage, factoryConfiguration.typeAliasesSuperType).stream()
                    .filter(clazz -> !clazz.isAnonymousClass()).filter(clazz -> !clazz.isInterface())
                    .filter(clazz -> !clazz.isMemberClass()).forEach(targetConfiguration.getTypeAliasRegistry()::registerAlias);
        }

        if (factoryConfiguration.typeAliases != null && factoryConfiguration.typeAliases.length > 0) {
            Stream.of(factoryConfiguration.typeAliases).forEach(typeAlias -> {
                targetConfiguration.getTypeAliasRegistry().registerAlias(typeAlias);
                LOGGER.debug("Registered type alias: '" + typeAlias + "'");
            });
        }

        if (factoryConfiguration.plugins != null && factoryConfiguration.plugins.length > 0) {
            Stream.of(factoryConfiguration.plugins).forEach(plugin -> {
                targetConfiguration.addInterceptor(plugin);
                LOGGER.debug("Registered plugin: '" + plugin + "'");
            });
        }

        if (StringUtils.hasLength(factoryConfiguration.typeHandlersPackage)) {
            scanClasses(factoryConfiguration.typeHandlersPackage, TypeHandler.class).stream().filter(clazz -> !clazz.isAnonymousClass())
                    .filter(clazz -> !clazz.isInterface()).filter(clazz -> !Modifier.isAbstract(clazz.getModifiers()))
                    .filter(clazz -> ReflectionHelper.getConstructorIfAvailable(clazz) != null)
                    .forEach(targetConfiguration.getTypeHandlerRegistry()::register);
        }

        if (factoryConfiguration.typeHandlers != null && factoryConfiguration.typeHandlers.length > 0) {
            Stream.of(factoryConfiguration.typeHandlers).forEach(typeHandler -> {
                targetConfiguration.getTypeHandlerRegistry().register(typeHandler);
                LOGGER.debug("Registered type handler: '" + typeHandler + "'");
            });
        }

        if (factoryConfiguration.scriptingLanguageDrivers != null && factoryConfiguration.scriptingLanguageDrivers.length > 0) {
            Stream.of(factoryConfiguration.scriptingLanguageDrivers).forEach(languageDriver -> {
                targetConfiguration.getLanguageRegistry().register(languageDriver);
                LOGGER.debug("Registered scripting language driver: '" + languageDriver + "'");
            });
        }
        Optional.ofNullable(factoryConfiguration.defaultScriptingLanguageDriver)
                .ifPresent(targetConfiguration::setDefaultScriptingLanguage);

        if (factoryConfiguration.databaseIdProvider != null) {// fix #64 set databaseId before parse mapper xmls
            try {
                targetConfiguration.setDatabaseId(factoryConfiguration.databaseIdProvider.getDatabaseId(factoryConfiguration.dataSource));
            } catch (SQLException e) {
                throw new IOException("Failed getting a databaseId", e);
            }
        }

        Optional.ofNullable(factoryConfiguration.cache).ifPresent(targetConfiguration::addCache);

        if (xmlConfigBuilder != null) {
            try {
                xmlConfigBuilder.parse();
                LOGGER.debug("Parsed configuration file: '" + factoryConfiguration.configLocation + "'");
            } catch (Exception ex) {
                throw new IOException("Failed to parse config resource: " + factoryConfiguration.configLocation, ex);
            } finally {
                ErrorContext.instance().reset();
            }
        }

        targetConfiguration.setEnvironment(new Environment(factoryConfiguration.environment,
                factoryConfiguration.transactionFactory == null ? new DebbieManagedTransactionFactory() : factoryConfiguration.transactionFactory,
                factoryConfiguration.dataSource));

        if (factoryConfiguration.mapperLocations != null) {
            if (factoryConfiguration.mapperLocations.length == 0) {
                LOGGER.warn("Property 'mapperLocations' was specified but matching resources are not found.");
            } else {
                for (String mapperLocation : factoryConfiguration.mapperLocations) {
                    if (mapperLocation == null) {
                        continue;
                    }
                    try {
                        XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(Resources.getResourceAsStream(mapperLocation),
                                targetConfiguration, mapperLocation, targetConfiguration.getSqlFragments());
                        xmlMapperBuilder.parse();
                    } catch (Exception e) {
                        throw new IOException("Failed to parse mapping resource: '" + mapperLocation + "'", e);
                    } finally {
                        ErrorContext.instance().reset();
                    }
                    LOGGER.debug("Parsed mapper file: '" + mapperLocation + "'");
                }
            }
        } else {
            LOGGER.debug("Property 'mapperLocations' was not specified.");
        }

        return factoryConfiguration.sqlSessionFactoryBuilder.build(targetConfiguration);
    }

    public void onApplicationEvent() {
        factoryConfiguration.sqlSessionFactory.getConfiguration().getMappedStatementNames();
    }

    private Set<Class<?>> scanClasses(String packagePatterns, Class<?> assignableType) throws IOException {
        Set<Class<?>> classes = new HashSet<>();

        return classes;
    }
}
