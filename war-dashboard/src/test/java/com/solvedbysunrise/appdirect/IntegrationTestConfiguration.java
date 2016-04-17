package com.solvedbysunrise.appdirect;

import com.solvedbysunrise.appdirect.config.DashboardConfiguration;
import org.springframework.context.annotation.Bean;

public class IntegrationTestConfiguration implements DashboardConfiguration {

    private static final String TOMCAT_BASE_URL = "http://localhost:8080/app-dashboard-war";

    @Override
    @Bean(name = "dashboardBaseUrl")
    public String dashboardBaseUrl() {
        return TOMCAT_BASE_URL;
    }
}
