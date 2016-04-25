package com.solvedbysunrise.appdirect.dto;

import org.joda.time.DateTime;

public class PurchaseDto {

    private String name;
    private String id;
    private String event;
    private String resourceLocation;
    private DateTime notificationTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getEvent() {
        return event;
    }

    public void setResourceLocation(String resourceLocation) {
        this.resourceLocation = resourceLocation;
    }

    public String getResourceLocation() {
        return resourceLocation;
    }

    public void setNotificationTime(DateTime notificationTime) {
        this.notificationTime = notificationTime;
    }

    public DateTime getNotificationTime() {
        return notificationTime;
    }
}
