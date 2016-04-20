package com.solvedbysunrise.appdirect.config;

import org.apache.commons.lang3.tuple.Pair;

import java.util.Collection;

public interface DashboardConfiguration {

    String databaseUrl();

    String dashboardBaseUrl();

    String testValue();

    Collection<Pair<String, String>> config();
}
