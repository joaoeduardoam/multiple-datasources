package com.joaoeduardoam.multipledatasources.post;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.jdbc.init.DataSourceScriptDatabaseInitializer;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.sql.init.DatabaseInitializationMode;
import org.springframework.boot.sql.init.DatabaseInitializationSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.joaoeduardoam.multipledatasources.post.repositories",
        entityManagerFactoryRef = "postEntityManagerFactory",
        transactionManagerRef = "postTransactionManager"
)
public class DbPostConfig {

    @Primary
    @Bean(name = "postDataSource")
    @ConfigurationProperties(prefix="spring.datasource.posts")
    public DataSource dbPostDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean("postEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean dbPostEntityManagerFactory(
                                                                    EntityManagerFactoryBuilder builder,
                                                                    @Qualifier("postDataSource") DataSource dataSource
                                                                    ) {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update");

        return builder.dataSource(dataSource)
                .properties(properties)
                .packages("com.joaoeduardoam.multipledatasources.post.entities")
                .persistenceUnit("Post")
                .build();
    }

    @Primary
    @Bean(name = "postTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("postEntityManagerFactory") LocalContainerEntityManagerFactoryBean postEntityManagerFactory
    ) {
        return new JpaTransactionManager(Objects.requireNonNull(postEntityManagerFactory.getObject()));
    }

    @Primary
    @Bean
    public JdbcClient postsJdbcClient(@Qualifier("postDataSource") DataSource dataSource) {
        return JdbcClient.create(dataSource);
    }

    @Primary
    @Bean
    DataSourceScriptDatabaseInitializer postsDbInit(@Qualifier("postDataSource") DataSource dataSource){
        var settings = new DatabaseInitializationSettings();
        settings.setSchemaLocations(List.of("classpath:posts-schema.sql"));
        settings.setMode(DatabaseInitializationMode.ALWAYS);
        return new DataSourceScriptDatabaseInitializer(dataSource,settings);
    }









}




















