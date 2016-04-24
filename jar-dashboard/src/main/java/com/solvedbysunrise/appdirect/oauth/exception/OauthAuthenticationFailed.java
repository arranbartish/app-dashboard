package com.solvedbysunrise.appdirect.oauth.exception;

public class OauthAuthenticationFailed extends RuntimeException {

    public OauthAuthenticationFailed(Throwable cause) {
        super("Failed to authenticate", cause);
    }

    public OauthAuthenticationFailed() {
        this(null);
    }
}
