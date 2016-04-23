package com.solvedbysunrise.appdirect;

import com.solvedbysunrise.appdirect.oauth.exception.OauthRequestSigningFailed;
import com.solvedbysunrise.appdirect.service.CreateSubscriptionRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.joda.time.DateTime.now;
import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/subscription/create/notification")
public class SubscriptionController {

    @Autowired
    private CreateSubscriptionRestService subscriptionRestService;

    @RequestMapping(method = GET)
    @ResponseStatus(code = ACCEPTED)
    public void getConfiguration(@RequestParam String url) {
        subscriptionRestService.createSubscription(url);
    }

}
