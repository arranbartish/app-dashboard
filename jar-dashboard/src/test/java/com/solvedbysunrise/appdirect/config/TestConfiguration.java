package com.solvedbysunrise.appdirect.config;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.context.annotation.Bean;

import java.util.Collection;

import static com.google.common.collect.Lists.newArrayList;
import static org.apache.commons.lang3.tuple.Pair.of;

public class TestConfiguration implements DashboardConfiguration{

    private static final String BASE_URL = "http://localhost:8080/";

    @Override
    @Bean(name = "dashboardBaseUrl")
    public String dashboardBaseUrl(){
        return BASE_URL;
    }


    @Override
    @Bean(name = "databaseUrl")
    public String databaseUrl() {
        return null;
    }

    @Override
    @Bean(name = "testValue")
    public String testValue() {
        return null;
    }

    @Override
    @Bean(name = "config")
    public Collection<Pair<String, String>> config() {
        return null;
    }
}
