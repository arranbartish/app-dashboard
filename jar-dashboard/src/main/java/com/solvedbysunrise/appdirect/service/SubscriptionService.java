package com.solvedbysunrise.appdirect.service;

import com.solvedbysunrise.appdirect.dao.EventDao;
import com.solvedbysunrise.appdirect.dto.EventDto;
import com.solvedbysunrise.appdirect.dto.PurchaseDto;
import com.solvedbysunrise.appdirect.entity.jpa.Event;
import com.solvedbysunrise.appdirect.enums.Type;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;

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
        EventDto dto = restService.getSubscriptionEvent(resourceUri);
        Event event = convertDtoToEvent(dto);
        eventDao.save(event);
    }

    private Event convertDtoToEvent(EventDto dto) {
        Event event = new Event();
        event.setNotificationTime(new Timestamp(DateTime.now().getMillis()));
        event.setId(dto.getCreator().getUuid());
        event.setResourceLocation(dto.getCreator().getOpenId());
        event.setEvent(Type.valueOf(dto.getType()));
        return event;
    }

    public Collection<PurchaseDto> getPurchases(){
        Collection<Event> events = newArrayList(eventDao.findAll());
        return events.parallelStream().map(this::convertEventToPurchase).collect(Collectors.toList());
    }

    private PurchaseDto convertEventToPurchase(Event event) {
        PurchaseDto purchase = new PurchaseDto();
        purchase.setId(event.getId());
        purchase.setEvent(event.getEvent().name());
        purchase.setResourceLocation(event.getResourceLocation());
        purchase.setNotificationTime((new DateTime()).withMillis(event.getNotificationTime().getTime()));
        return purchase;
    }

}
