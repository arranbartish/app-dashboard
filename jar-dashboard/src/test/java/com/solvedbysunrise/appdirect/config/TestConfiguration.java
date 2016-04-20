package com.solvedbysunrise.appdirect.config;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.context.annotation.Bean;

import java.util.Collection;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.apache.commons.lang3.tuple.Pair.of;

public class TestConfiguration extends ProductionConfiguration {

    private static final String BASE_URL = "http://localhost:8080/";

    @Override
    @Bean(name = "dashboardBaseUrl")
    public String dashboardBaseUrl(){
        return BASE_URL;
    }
}
