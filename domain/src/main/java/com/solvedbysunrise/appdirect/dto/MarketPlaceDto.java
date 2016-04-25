package com.solvedbysunrise.appdirect.dto;

import com.solvedbysunrise.appdirect.RefelctiveBean;

public class MarketPlaceDto extends RefelctiveBean {

    public String partner;

    public String baseUrl;

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}
