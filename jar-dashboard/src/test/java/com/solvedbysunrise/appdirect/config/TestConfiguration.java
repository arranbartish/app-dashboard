package com.solvedbysunrise.appdirect.config;

import org.springframework.context.annotation.Bean;

public class TestConfiguration implements DashboardConfiguration{

    private static final String BASE_URL = "http://localhost:8080/";

    @Override
    @Bean(name = "dashboardBaseUrl")
    public String dashboardBaseUrl(){
        return BASE_URL;
    }
}
