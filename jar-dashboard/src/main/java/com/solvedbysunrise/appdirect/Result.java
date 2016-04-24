package com.solvedbysunrise.appdirect;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "result")
public class Result extends RefelctiveBean {

    private boolean success;
    private String message;
    private String accountIdentifier;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAccountIdentifier() {
        return accountIdentifier;
    }

    public void setAccountIdentifier(String accountIdentifier) {
        this.accountIdentifier = accountIdentifier;
    }
}
