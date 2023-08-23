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
@RequiredArgsConstructor
@EnableTransactionManagement
@EnableJpaRepositories(
        transactionManagerRef = "database2TransactionManager",
        entityManagerFactoryRef = "database2EntityManagerFactory",
        basePackages = {"com.example.prog4.repository.database2",
        }
)
public class ConfigurationEmployeeCnaps {
    private final Environment env;

    @Bean(initMethod = "migrate")
    @ConfigurationProperties(prefix = "spring.flyway.database2")
    public Flyway flywayDatabase2() {
        return new Flyway(
                Flyway.configure()
                        .baselineOnMigrate(true)
                        .locations("classpath:/db/migration/database2")
                        .dataSource(
                                env.getRequiredProperty("spring.second-datasource.url"),
                                env.getRequiredProperty("spring.second-datasource.username"),
                                env.getRequiredProperty("spring.second-datasource.password")
                        )
        );
    }

    @Bean(name = "database2Datasource")
    @ConfigurationProperties(prefix = "spring.second-datasource")
    public DataSource databaseb2DataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "database2EntityManagerFactoryBuilder")
    public EntityManagerFactoryBuilder database2EntityManagerFactoryBuilder(
            @Qualifier("database2Datasource") DataSource dataSource
    ) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence.jdbc.url", env.getRequiredProperty("spring.second-datasource.url"));
        properties.put("javax.persistence.jdbc.user", env.getRequiredProperty("spring.second-datasource.username"));
        properties.put("javax.persistence.jdbc.password", env.getRequiredProperty("spring.second-datasource.password"));

        return new EntityManagerFactoryBuilder(
                new HibernateJpaVendorAdapter(),
                properties,
                null
        );
    }

    @Bean(name = "database2EntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean database2EntityManagerFactory(
            @Qualifier("database2EntityManagerFactoryBuilder") final EntityManagerFactoryBuilder builder,
            @Qualifier("database2Datasource") final DataSource dataSource
    ) {
        return builder
                .dataSource(dataSource)
                .packages("com.example.prog4.repository.database2.entity")
                .build();
    }

    @Bean(name = "database2TransactionManager")
    public PlatformTransactionManager database2PlatformTransactionManager(
            @Qualifier("database2EntityManagerFactory") final EntityManagerFactory database2EntityManagerFactory
    ) {
        return new JpaTransactionManager(database2EntityManagerFactory);
    }
}