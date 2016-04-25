package com.solvedbysunrise.appdirect.controller;

import com.solvedbysunrise.appdirect.dto.PurchaseDto;
import com.solvedbysunrise.appdirect.service.SubscriptionService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(value = "/purchases",
        produces = {APPLICATION_JSON_UTF8_VALUE})
public class PurchaseController {

    private static final Logger LOGGER = getLogger(PurchaseController.class);

    private final SubscriptionService subscriptionService;

    @Autowired
    public PurchaseController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @RequestMapping(method = GET)
    @ResponseStatus(code = ACCEPTED)
    public Collection<PurchaseDto> getPurchases() {
        return subscriptionService.getPurchases();
    }
}
