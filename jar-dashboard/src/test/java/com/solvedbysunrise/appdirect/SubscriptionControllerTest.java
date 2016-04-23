package com.solvedbysunrise.appdirect;

import com.solvedbysunrise.appdirect.config.TestConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import java.net.URI;

import static com.solvedbysunrise.appdirect.MockServerController.EVENT_PATH;
import static com.solvedbysunrise.appdirect.ResultFactory.successfulResult;
import static com.solvedbysunrise.appdirect.SubscriptionController.SUCCESS_MESSAGE;
import static com.solvedbysunrise.appdirect.util.UriUtil.getFullyQualifiedUriPattern;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
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

    private static final String TOKEN = "A-TOKEN";
    private static final Result EXPECTED_SUCCESS_RESULT= successfulResult(SUCCESS_MESSAGE,TOKEN);

    private static final String SUBSCRIPTION_URI = "/subscription/create/notification?resource={resource}&token={token}";

    private static final String SUBSCRIPTION_RESOURCE = EVENT_PATH;

    private static final String EXPECTED_SUCCESS_XML_RESPONSE = String.format("<result xmlns=\"\"><success>%s</success><message>%s</message><accountIdentifier>%s</accountIdentifier></result>",
            EXPECTED_SUCCESS_RESULT.isSuccess(), EXPECTED_SUCCESS_RESULT.getMessage(), EXPECTED_SUCCESS_RESULT.getAccountIdentifier());

    private URI fullSubscriptionResourceUri;

    private URI fullSubscriptionRequestUri;

    @Before
    public void generateUrls(){
        fullSubscriptionResourceUri = new UriTemplate(getFullyQualifiedUriPattern(dashboardBaseUrl, SUBSCRIPTION_RESOURCE)).expand(TOKEN);
        fullSubscriptionRequestUri =  new UriTemplate(
                getFullyQualifiedUriPattern(dashboardBaseUrl, SUBSCRIPTION_URI))
                .expand(fullSubscriptionResourceUri, TOKEN);
    }

    @Test
    public void create_subscription_will_return_accepted() throws Exception {

        HttpEntity<?> entity = new HttpEntity<>(GET);


        ResponseEntity<Result> response = restTemplate.exchange(
                fullSubscriptionRequestUri,
                GET, entity, Result.class);

        assertThat(response.getStatusCode(), is(HttpStatus.ACCEPTED));
    }

    @Test
    public void create_subscription_will_return_success_result() throws Exception {
        ResponseEntity<Result> response = restTemplate.getForEntity(fullSubscriptionRequestUri,
                Result.class);

        assertThat(response.getBody(), is(EXPECTED_SUCCESS_RESULT));
    }

    @Test
    public void create_subscription_will_return_valid_XML() throws Exception {

        HttpEntity<?> entity = new HttpEntity<>(GET);

        ResponseEntity<String> response = restTemplate.exchange(
                fullSubscriptionRequestUri,
                GET, entity, String.class);

        assertThat(response.getBody(), is(EXPECTED_SUCCESS_XML_RESPONSE));
    }

}