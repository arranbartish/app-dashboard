package com.solvedbysunrise.appdirect.exception;

public class ContentUnreadable extends RuntimeException {

    public ContentUnreadable(String message) {
        super(message);
    }

    public ContentUnreadable(String message, Throwable cause) {
        super(message, cause);
    }
}
