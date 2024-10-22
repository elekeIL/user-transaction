package com.transaction.configurations;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.util.Objects;

/**
 * @author Eleke Iheanyi
 * email: iheanyi.eleke@gmail.com
 * May, 2024
 **/

@Configuration
@EnableJpaRepositories(
        basePackages = "com.transaction.repository",
        entityManagerFactoryRef = "defaultEntityManager",
        transactionManagerRef = "defaultTransactionManager"
)
@RequiredArgsConstructor
public class TransactionManagerConfiguration {

        @Value("${HIBERNATE_BATCH_INSERT_SIZE:1000}")
        @Getter
        private String hibernateBatchSize;

        @Bean
        @ConfigurationProperties("spring.datasource")
        public DataSourceProperties defaultDataSourceProperties() {
            return new DataSourceProperties();
        }

        @Bean
        @Primary
        public DataSource defaultDataSource() {
            return defaultDataSourceProperties()
                    .initializeDataSourceBuilder()
                    .build();
        }

        @Bean
        @Primary
        public LocalContainerEntityManagerFactoryBean defaultEntityManager(@Qualifier("defaultDataSource") DataSource dataSource,
                                                                           HibernateProperties hibernateProperties,
                                                                           JpaProperties jpaProperties,
                                                                           EntityManagerFactoryBuilder builder) {
            return builder
                    .dataSource(dataSource)
                    .properties(hibernateProperties.determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings()))
                    .packages("com.transaction")
                    .build();
        }

        @Bean
        @Primary
        public PlatformTransactionManager defaultTransactionManager(
                @Qualifier("defaultEntityManager") LocalContainerEntityManagerFactoryBean entityManagerFactory) {
            return new JpaTransactionManager(Objects.requireNonNull(entityManagerFactory.getObject()));
        }

        @Bean
        @Primary
        public TransactionTemplate defaultTransactionTemplate(@Qualifier("defaultTransactionManager") PlatformTransactionManager transactionManager) {
            return new TransactionTemplate(transactionManager);
        }
}
