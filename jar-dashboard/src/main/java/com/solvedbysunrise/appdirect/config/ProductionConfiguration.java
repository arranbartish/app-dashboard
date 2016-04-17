package com.solvedbysunrise.appdirect.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class ProductionConfiguration implements DashboardConfiguration {

    @Value("${baseUrl:http://localhost:8080/}")
    private String baseUrl;

    @Override
    @Bean(name = "dashboardBaseUrl")
    public String dashboardBaseUrl() {
        return baseUrl;
    }

}
