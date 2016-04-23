package com.solvedbysunrise.appdirect.service;

import com.solvedbysunrise.appdirect.SubscriptionController;
import com.solvedbysunrise.appdirect.oauth.HttpRequestSigningService;
import com.solvedbysunrise.appdirect.oauth.UriTemplateHttpRequest;
import com.solvedbysunrise.appdirect.oauth.UriTemplateHttpRequestBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static com.solvedbysunrise.appdirect.oauth.UriTemplateHttpRequestBuilder.withUrl;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;

@Service
public class CreateSubscriptionRestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateSubscriptionRestService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HttpRequestSigningService signingService;

    public void createSubscription(final String resourceUri) {

        UriTemplateHttpRequest signedRequest = signingService.sign(
                withUrl(resourceUri)
                        .method(GET)
                        .mediaType(APPLICATION_JSON_UTF8)
                        .build());

        HttpEntity entity = new HttpEntity(signedRequest.getHeaders());

        ResponseEntity<String> response = restTemplate.exchange(
                signedRequest.getRequestUri(),
                signedRequest.getHttpMethod(), entity, String.class);

        LOGGER.info(response.getBody());
    }
}
