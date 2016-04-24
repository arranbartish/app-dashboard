package com.solvedbysunrise.appdirect;

import com.solvedbysunrise.appdirect.service.CreateSubscriptionRestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.solvedbysunrise.appdirect.ResultFactory.failureResult;
import static com.solvedbysunrise.appdirect.ResultFactory.successfulResult;
import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(value = "/subscription",
        produces = {APPLICATION_XML_VALUE})
public class SubscriptionController {

    public static final String SUCCESS_MESSAGE = "Account is accepted and will be completed shortly";
    public static final String FAILURE_MESSAGE = "This is bad";

    private final static Logger LOGGER = LoggerFactory.getLogger(SubscriptionController.class);

    @Autowired
    private CreateSubscriptionRestService subscriptionRestService;

    @RequestMapping(value = "/create/notification",method = GET)
    @ResponseStatus(code = ACCEPTED)
    public Result createSubscription(HttpServletRequest request, @RequestParam String resource, @RequestParam String token) {

        LOGGER.info(String.format("key: resource value: %s", resource));
        LOGGER.info(String.format("key: token value: %s", resource));

        subscriptionRestService.createSubscription(resource);
        return successfulResult(SUCCESS_MESSAGE, token);
    }


    @RequestMapping(value = "/cancel/notification",method = GET)
    @ResponseStatus(code = ACCEPTED)
    public Result cancelSubscription(@RequestParam String resource, @RequestParam String token) {

        LOGGER.info(String.format("key: resource value: %s", resource));
        LOGGER.info(String.format("key: token value: %s", resource));

        return successfulResult(SUCCESS_MESSAGE, token);
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result error() {
        return failureResult(FAILURE_MESSAGE);
    }
}
