package com.solvedbysunrise.appdirect.service;

import com.solvedbysunrise.appdirect.dao.EventDao;
import com.solvedbysunrise.appdirect.dto.EventDTO;
import com.solvedbysunrise.appdirect.dto.Purchase;
import com.solvedbysunrise.appdirect.entity.jpa.Event;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;
import static com.solvedbysunrise.appdirect.enums.Type.SUBSCRIBE;

@Service
@Transactional
public class SubscriptionService {

    private final EventDao eventDao;

    private final AppDirectRestService restService;

    @Autowired
    public SubscriptionService(final EventDao eventDao, final AppDirectRestService restService) {
        this.eventDao = eventDao;
        this.restService = restService;
    }

    public void createSubscriptionEvent(final String resourceUri){
        EventDTO dto = restService.getSubscriptionEvent(resourceUri);
        Event event = convertDtoToEvent(dto);
        eventDao.save(event);
    }

    private Event convertDtoToEvent(EventDTO dto) {
        Event event = new Event();
        event.setNotificationTime(new Timestamp(DateTime.now().getMillis()));
        event.setId("id");
        event.setResourceLocation("location");
        event.setEvent(SUBSCRIBE);
        return event;
    }

    public Collection<Purchase> getPurchases(){
        Collection<Event> events = newArrayList(eventDao.findAll());
        return events.parallelStream().map(this::convertEventToPurchase).collect(Collectors.toList());
    }

    private Purchase convertEventToPurchase(Event event) {
        Purchase purchase = new Purchase();
        purchase.setName("something");
        return purchase;
    }

}
