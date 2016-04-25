package com.solvedbysunrise.appdirect.dto.factories;

import com.solvedbysunrise.appdirect.dto.ResultDto;

public class ResultFactory {


    public static ResultDto successfulResult(final String message, final String accountId) {
        ResultDto result = new ResultDto();
        result.setSuccess(true);
        result.setMessage(message);
        result.setAccountIdentifier(accountId);
        return result;
    }

    public static ResultDto failureResult(final String message) {
        ResultDto result = new ResultDto();
        result.setSuccess(false);
        result.setMessage(message);
        return result;
    }
}
