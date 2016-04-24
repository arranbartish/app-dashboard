package com.solvedbysunrise.appdirect;

public class ResultFactory {


    public static Result successfulResult(final String message, final String accountId) {
        Result result = new Result();
        result.setSuccess(true);
        result.setMessage(message);
        result.setAccountIdentifier(accountId);
        return result;
    }

    public static Result failureResult(final String message) {
        Result result = new Result();
        result.setSuccess(false);
        result.setMessage(message);
        return result;
    }
}
