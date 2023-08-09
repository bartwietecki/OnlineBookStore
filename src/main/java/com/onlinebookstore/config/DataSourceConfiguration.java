package com.onlinebookstore.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfiguration {
    @Bean
    public DataSource getDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
        dataSourceBuilder.url(System.getenv("DB_URL"));
        dataSourceBuilder.username(System.getenv("DB_USER"));
        dataSourceBuilder.password(System.getenv("DB_PASSWORD"));
        return dataSourceBuilder.build();
    }
}