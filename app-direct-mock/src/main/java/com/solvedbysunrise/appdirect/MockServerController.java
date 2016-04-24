package com.solvedbysunrise.appdirect;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
public class MockServerController {

    public final static String EVENT_PATH = "/mockserver/api/integration/v1/events/{token}";

    private AppDirectEventService eventService;

    @Autowired
    public MockServerController(AppDirectEventService eventService) {
        this.eventService = eventService;
    }

    @RequestMapping(value = EVENT_PATH,
            produces = {APPLICATION_JSON_UTF8_VALUE})
    public String pretendToBeAppDirect(@PathVariable String token){
        return eventService.getEventDetail(token);
    }
}
