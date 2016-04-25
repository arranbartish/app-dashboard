package com.solvedbysunrise.appdirect.config;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.util.Collection;

import static com.google.common.collect.Lists.newArrayList;
import static org.apache.commons.lang3.tuple.Pair.of;

public class ProductionConfiguration implements DashboardConfiguration {

    @Value("${baseUrl:http://localhost:8080/}")
    private String baseUrl;

    @Value("${DATABASE_URL:did-not-resolve}")
    private String databaseUrl;

    @Value("${testValue:also-did-not-work}")
    private String testValue;

    @Value("${appdirect.api.key:not-my-key}")
    private String appdirectApiKey;

    @Value("${appdirect.oauth.consumer.key:changed-key}")
    private String oauthConsumerKey;

    @Value("${appdirect.oauth.consumer.secret:changed-secret}")
    private String oauthConsumerSecret;

    @Override
    @Bean(name = "dashboardBaseUrl")
    public String dashboardBaseUrl() {
        return baseUrl;
    }

    @Override
    @Bean(name = "databaseUrl")
    public String databaseUrl() {
        return databaseUrl;
    }

    @Override
    @Bean(name = "testValue")
    public String testValue() {
        return testValue;
    }

    @Override
    @Bean(name = "config")
    public Collection<Pair<String, String>> config() {
        return newArrayList(
                of("database", databaseUrl()),
                of("url", dashboardBaseUrl()),
                of("test", testValue()));
    }

    @Override
    @Bean(name = "appdirectApiKey")
    public String appdirectApiKey() {
        return appdirectApiKey;
    }

    @Override
    @Bean(name = "oauthConsumerKey")
    public String oauthConsumerKey() {
        return oauthConsumerKey;
    }

    @Override
    @Bean(name = "oauthConsumerSecret")
    public String oauthConsumerSecret() {
        return oauthConsumerSecret;
    }
}
