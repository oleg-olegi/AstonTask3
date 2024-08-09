package org.example.repository;

import jakarta.persistence.EntityManagerFactory;
import org.example.entity.Author;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;

import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;
import java.util.Properties;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringJUnitConfig(AuthorRepositoryTest.Config.class)
@Testcontainers
@ComponentScan(basePackages = "org.example.repository")

public class AuthorRepositoryTest {

    @Container
    public static PostgreSQLContainer<?> postgresContainer =  new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    @BeforeAll
    public static void start() {
        postgresContainer.start();
    }

    @AfterAll
    public static void teardown() {
        postgresContainer.stop();
    }

    @Configuration
    @EnableJpaRepositories(basePackages = "org.example.repository")
    @PropertySource("classpath:application-test.properties")
    static class Config {

        private final Environment env;

        Config(Environment env) {
            this.env = env;
        }

        @Bean
        public DataSource dataSource() {
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName("org.h2.Driver");
            dataSource.setUrl(env.getProperty("jdbc.url"));
            dataSource.setUsername(env.getProperty("jdbc.username"));
            dataSource.setPassword(env.getProperty("jdbc.password"));
            return dataSource;
        }

        @Bean
        public EntityManagerFactory entityManagerFactory(DataSource dataSource) {
            LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
            em.setDataSource(dataSource);
            em.setPackagesToScan("org.example");
            em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
            em.setJpaProperties(additionalProperties());
            em.afterPropertiesSet();
            return em.getObject();
        }

        private Properties additionalProperties() {
            Properties properties = new Properties();
            properties.put("hibernate.dialect", env.getProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect"));
            properties.put("hibernate.show_sql", env.getProperty("hibernate.show_sql", "true"));
            properties.put("hibernate.format_sql", env.getProperty("hibernate.format_sql", "true"));
            properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto", "update"));
            return properties;
        }

        @Bean
        public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
            JpaTransactionManager transactionManager = new JpaTransactionManager();
            transactionManager.setEntityManagerFactory(emf);
            return transactionManager;
        }

        @Bean
        AuthorRepository authorRepository(EntityManagerFactory emf) {
            return emf.unwrap(AuthorRepository.class);
        }
    }

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    void testSaveAndFind() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        authorRepository = context.getBean(AuthorRepository.class);

        Author author = new Author();
        author.setId(1L);
        author.setName("John Doe");
        authorRepository.save(author);

        Author foundAuthor = authorRepository.findById(author.getId()).orElse(null);
        assertThat(foundAuthor).isNotNull();
        assert foundAuthor != null;
        assertThat(foundAuthor.getName()).isEqualTo("John Doe");

        context.close();
    }
}
