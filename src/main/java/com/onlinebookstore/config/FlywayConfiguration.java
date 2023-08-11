package com.onlinebookstore.config;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class FlywayConfiguration {

    private final DataSource dataSource;

    public FlywayConfiguration(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean(initMethod = "migrate")
    public Flyway flyway() {
        FluentConfiguration fluentConfiguration = Flyway.configure().dataSource(dataSource).baselineOnMigrate(true)
                .locations("classpath:db/migration");
        return new Flyway(fluentConfiguration);
    }
}