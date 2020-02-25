package net.medrag.account_service.config;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.ClassicConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlywayConfig {

    @Value("${spring.datasource.url}")
    private String dataSourceUrl;
    @Value("${spring.datasource.username}")
    private String dataSourceUsername;
    @Value("${spring.datasource.password}")
    private String dataSourcePassword;

    @Bean(initMethod = "migrate")
    public Flyway flyway() {
        ClassicConfiguration c = new ClassicConfiguration();
        c.setDataSource(dataSourceUrl, dataSourceUsername, dataSourcePassword);
        c.setBaselineOnMigrate(true);
        return new Flyway(c);
    }
}
