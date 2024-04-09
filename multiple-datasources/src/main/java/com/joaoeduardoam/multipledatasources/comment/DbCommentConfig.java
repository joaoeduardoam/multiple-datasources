package com.joaoeduardoam.multipledatasources.comment;


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
        basePackages = "com.joaoeduardoam.multipledatasources.comment.repositories",
        entityManagerFactoryRef = "commentEntityManagerFactory",
        transactionManagerRef = "commentTransactionManager"
)
public class DbCommentConfig {

    @Bean(name = "commentDataSource")
    @ConfigurationProperties(prefix="spring.datasource.comments")
    public DataSource dbCommentDataSource() {
        return DataSourceBuilder.create().build();
    }


    @Bean("commentEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean dbCommentEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("commentDataSource") DataSource dataSource
    ) {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update");

        return builder.dataSource(dataSource)
                .properties(properties)
                .packages("com.joaoeduardoam.multipledatasources.comment.entities")
                .persistenceUnit("Comment")
                .build();
    }

    @Bean(name = "commentTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("commentEntityManagerFactory") LocalContainerEntityManagerFactoryBean commentEntityManagerFactory
    ) {
        return new JpaTransactionManager(Objects.requireNonNull(commentEntityManagerFactory.getObject()));
    }

    @Bean
    public JdbcClient commentsJdbcClient(@Qualifier("commentDataSource") DataSource dataSource) {
        return JdbcClient.create(dataSource);
    }

    @Bean
    DataSourceScriptDatabaseInitializer commentsDbInit(@Qualifier("commentDataSource") DataSource dataSource){
        var settings = new DatabaseInitializationSettings();
        settings.setSchemaLocations(List.of("classpath:comments-schema.sql"));
        settings.setMode(DatabaseInitializationMode.ALWAYS);
        return new DataSourceScriptDatabaseInitializer(dataSource,settings);
    }

}



















