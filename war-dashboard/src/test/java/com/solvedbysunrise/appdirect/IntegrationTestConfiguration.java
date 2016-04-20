package com.solvedbysunrise.appdirect;

import com.solvedbysunrise.appdirect.config.DashboardConfiguration;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.context.annotation.Bean;

import java.util.Collection;

public class IntegrationTestConfiguration implements DashboardConfiguration {

    private static final String TOMCAT_BASE_URL = "http://localhost:8081/app-dashboard-war";

    @Override
    @Bean(name = "dashboardBaseUrl")
    public String dashboardBaseUrl() {
        return TOMCAT_BASE_URL;
    }

    @Override
    public String databaseUrl() {
        return null;
    }

    @Override
    public String testValue() {
        return null;
    }

    @Override
    public Collection<Pair<String, String>> config() {
        return null;
    }
}
