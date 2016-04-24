package com.solvedbysunrise.appdirect.enums;

import org.apache.commons.lang3.StringUtils;

public enum User {


    APP_DIRECT("AppDirect", "API");

    public static final String DEFAULT_ROLE_PREFIX = "ROLE_";

    private final String name;
    private final String authority;
    private final String role;

    User(String name, String authority) {
        this.name = name;
        this.authority = authority;
        this.role = StringUtils.join(DEFAULT_ROLE_PREFIX, authority);
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public String getAuthority() {
        return authority;
    }
}
