package com.solvedbysunrise.appdirect.dto;

import com.solvedbysunrise.appdirect.RefelctiveBean;

public class EventDto extends RefelctiveBean {

    public String type;
    public MarketPlaceDto marketplace;
    public String applicationUuid;
    public String flag;
    public CreatorDto creator;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public MarketPlaceDto getMarketplace() {
        return marketplace;
    }

    public void setMarketplace(MarketPlaceDto marketplace) {
        this.marketplace = marketplace;
    }

    public String getApplicationUuid() {
        return applicationUuid;
    }

    public void setApplicationUuid(String applicationUuid) {
        this.applicationUuid = applicationUuid;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public CreatorDto getCreator() {
        return creator;
    }

    public void setCreator(CreatorDto creator) {
        this.creator = creator;
    }
}
