package com.example.prog4.repository.config;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@RequiredArgsConstructor
@EnableJpaRepositories(
        transactionManagerRef = "database1TransactionManager",
        entityManagerFactoryRef = "database1EntityManagerFactory",
        basePackages = {"com.example.prog4.repository.database1"}
)
public class ConfigurationEmployee {
    private final Environment env;

    @Bean(initMethod = "migrate")
    @ConfigurationProperties(prefix = "spring.flyway.database1")
    public Flyway flywayDatabase1() {
        return new Flyway(
                Flyway.configure()
                        .baselineOnMigrate(true)
                        .locations("classpath:/db/migration/database1")
                        .dataSource(
                                env.getRequiredProperty("spring.datasource.url"),
                                env.getRequiredProperty("spring.datasource.username"),
                                env.getRequiredProperty("spring.datasource.password")
                        )
        );
    }

    @Bean(name = "database1Datasource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource database1DataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "database1EntityManagerFactoryBuilder")
    public EntityManagerFactoryBuilder database1EntityManagerFactoryBuilder(
            @Qualifier("database1Datasource") DataSource dataSource
    ) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence.jdbc.url", env.getRequiredProperty("spring.datasource.url"));
        properties.put("javax.persistence.jdbc.user", env.getRequiredProperty("spring.datasource.username"));
        properties.put("javax.persistence.jdbc.password", env.getRequiredProperty("spring.datasource.password"));

        return new EntityManagerFactoryBuilder(
                new HibernateJpaVendorAdapter(),
                properties,
                null
        );
    }

    @Primary
    @Bean(name = "database1EntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean database1EntityManagerFactory(
            @Qualifier("database1EntityManagerFactoryBuilder") final EntityManagerFactoryBuilder builder,
            @Qualifier("database1Datasource") final DataSource dataSource
    ) {
        return  builder
                .dataSource(dataSource)
                .packages("com.example.prog4.repository.database1.entity")
                .build();
    }


    @Primary
    @Bean(name = "database1TransactionManager")
    public PlatformTransactionManager database1PlatformTransactionManager(
            @Qualifier("database1EntityManagerFactory") final EntityManagerFactory database1EntityManagerFactory
    ) {
        return new JpaTransactionManager(database1EntityManagerFactory);
    }
}