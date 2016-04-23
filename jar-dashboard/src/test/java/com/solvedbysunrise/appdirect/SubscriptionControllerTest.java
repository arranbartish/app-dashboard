package com.solvedbysunrise.appdirect;

import com.solvedbysunrise.appdirect.config.TestConfiguration;
import com.solvedbysunrise.appdirect.dto.Hello;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import java.net.URI;

import static com.solvedbysunrise.appdirect.util.UriUtil.getFullyQualifiedUriPattern;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.springframework.http.HttpMethod.GET;

@Rollback
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {AppDirectDashboardApplication.class, TestConfiguration.class})
@WebIntegrationTest
public class SubscriptionControllerTest {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private String dashboardBaseUrl;

    private static final String SUBSCRIPTION_URI = "/subscription/create/notification?url={resource}";

    private static final String SUBSCRIPTION_RESOURCE = "/hello";

    private URI fullSubscriptionResourceUri;

    private URI fullSubscriptionRequestUri;

    @Before
    public void generateUrls(){
        fullSubscriptionResourceUri = new UriTemplate(getFullyQualifiedUriPattern(dashboardBaseUrl, "/hello")).expand();
        fullSubscriptionRequestUri =  new UriTemplate(
                getFullyQualifiedUriPattern(dashboardBaseUrl, SUBSCRIPTION_URI))
                .expand(fullSubscriptionResourceUri);
    }

    @Test
    public void create_subscription_will_return_accepted() throws Exception {

        HttpEntity entity = new HttpEntity("");
        ResponseEntity<String> response = restTemplate.exchange(
                fullSubscriptionRequestUri,
                GET, entity, String.class);

        assertThat(response.getStatusCode(), is(HttpStatus.ACCEPTED));
    }
}