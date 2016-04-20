package com.solvedbysunrise.appdirect;

import com.solvedbysunrise.appdirect.config.DashboardConfiguration;
import com.solvedbysunrise.appdirect.config.TestConfiguration;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.context.annotation.Bean;

import java.util.Collection;

public class IntegrationTestConfiguration extends TestConfiguration {

    private static final String TOMCAT_BASE_URL = "http://localhost:8081/app-dashboard-war";

    @Override
    @Bean(name = "dashboardBaseUrl")
    public String dashboardBaseUrl() {
        return TOMCAT_BASE_URL;
    }
}
