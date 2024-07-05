package com.rds.connect.common.config;

import com.rds.connect.common.util.db.SSHTunnelingInitializer;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Profile("dev")
@Configuration
@RequiredArgsConstructor
public class DataSourceConfig {
    private final SSHTunnelingInitializer initializer;

    @Bean("dataSource")
    @Primary
    public DataSource dataSource(DataSourceProperties properties) {
        Integer forwardedPort = initializer.buildSSHConnection();
        String url = properties.getUrl().replace("[forwardedPort]", Integer.toString(forwardedPort));

        return DataSourceBuilder.create()
                .url(url)
                .username(properties.getUsername())
                .password(properties.getPassword())
                .driverClassName(properties.getDriverClassName())
                .build();
    }
}
