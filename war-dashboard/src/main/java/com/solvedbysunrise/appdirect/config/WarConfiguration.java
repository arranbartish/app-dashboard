package com.solvedbysunrise.appdirect.config;

import com.solvedbysunrise.appdirect.config.ProductionConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class WarConfiguration extends ProductionConfiguration {

    @Value("${baseUrl:http://localhost:8081/app-dashboard-war}")
    private String baseUrl;

    @Override
    @Bean(name = "dashboardBaseUrl")
    public String dashboardBaseUrl() {
        return baseUrl;
    }


    @Override
    @Bean(name = "databaseUrl")
    public String databaseUrl() {
        return "blah";
    }
}
