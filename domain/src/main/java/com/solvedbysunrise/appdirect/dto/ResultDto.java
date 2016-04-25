package com.solvedbysunrise.appdirect.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.solvedbysunrise.appdirect.RefelctiveBean;

@JsonRootName(value = "result")
public class ResultDto extends RefelctiveBean {

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
