package com.solvedbysunrise.appdirect.service;

import com.solvedbysunrise.appdirect.dto.EventDTO;
import com.solvedbysunrise.appdirect.oauth.HttpRequestSigningService;
import com.solvedbysunrise.appdirect.oauth.UriTemplateHttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.solvedbysunrise.appdirect.oauth.UriTemplateHttpRequestBuilder.withUrl;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;

@Service
public class AppDirectRestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppDirectRestService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HttpRequestSigningService signingService;

    public EventDTO getSubscriptionEvent(final String resourceUri) {
        return getEvent(resourceUri);
    }

    private EventDTO getEvent(String resourceUri) {
        UriTemplateHttpRequest signedRequest = signingService.sign(
                withUrl(resourceUri)
                        .method(GET)
                        .mediaType(APPLICATION_JSON_UTF8)
                        .build());

        HttpEntity entity = new HttpEntity(signedRequest.getHeaders());

        ResponseEntity<EventDTO> response = restTemplate.exchange(
                signedRequest.getRequestUri(),
                signedRequest.getHttpMethod(), entity, EventDTO.class);


        LOGGER.info(response.getBody().toString());
        return response.getBody();
    }
}
