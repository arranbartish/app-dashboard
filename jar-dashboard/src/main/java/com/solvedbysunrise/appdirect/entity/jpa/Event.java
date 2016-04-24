package com.solvedbysunrise.appdirect.entity.jpa;

import com.solvedbysunrise.appdirect.RefelctiveBean;
import com.solvedbysunrise.appdirect.enums.Type;

import javax.persistence.*;
import java.sql.Timestamp;

import static javax.persistence.EnumType.STRING;

@Entity
@Table(indexes = {
        @Index(name = "event_id",  columnList="id", unique = true)
})
public class Event extends RefelctiveBean {

    @Id
    private String id;

    @Enumerated(STRING)
    private Type event;

    private Timestamp notificationTime;

    private String resourceLocation;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Type getEvent() {
        return event;
    }

    public void setEvent(Type event) {
        this.event = event;
    }

    public Timestamp getNotificationTime() {
        return notificationTime;
    }

    public void setNotificationTime(Timestamp notificationTime) {
        this.notificationTime = notificationTime;
    }

    public String getResourceLocation() {
        return resourceLocation;
    }

    public void setResourceLocation(String resourceLocation) {
        this.resourceLocation = resourceLocation;
    }
}