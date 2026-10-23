package ru.skillfactory.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

//@Configuration
//@ComponentScan(basePackages = "ru.skillfactory")
public class AppConfig {
    @Bean
    public DataSource dataSource() {
        // В реальном приложении на Spring Boot 3.x используется автоконфигурация DataSource (HikariCP); ниже — учебный пример ручной конфигурации.
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setUrl("jdbc:postgresql://localhost:5432/your_database");
        ds.setUsername("postgres");
        ds.setPassword("your_password");
        return ds;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        // JdbcTemplate получает готовый DataSource для работы с БД
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
        // NamedParameterJdbcTemplate использует тот же DataSource
        return new NamedParameterJdbcTemplate(dataSource());
    }
}
