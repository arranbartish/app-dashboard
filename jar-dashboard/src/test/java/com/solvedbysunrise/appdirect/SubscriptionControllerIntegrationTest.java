package com.solvedbysunrise.appdirect;

import com.solvedbysunrise.appdirect.config.TestConfiguration;
import com.solvedbysunrise.appdirect.dto.ResultDto;
import com.solvedbysunrise.appdirect.oauth.HttpRequestSigningService;
import com.solvedbysunrise.appdirect.oauth.OAuthConsumerFactory;
import com.solvedbysunrise.appdirect.oauth.UriTemplateHttpRequest;
import org.junit.Assert;
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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import java.net.URI;

import static com.solvedbysunrise.appdirect.MockServerController.EVENT_PATH;
import static com.solvedbysunrise.appdirect.dto.factories.ResultFactory.successfulResult;
import static com.solvedbysunrise.appdirect.controller.SubscriptionController.SUCCESS_MESSAGE;
import static com.solvedbysunrise.appdirect.oauth.UriTemplateHttpRequestBuilder.withUrl;
import static com.solvedbysunrise.appdirect.util.UriUtil.getFullyQualifiedUriPattern;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_XML;

@Rollback
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {AppDirectDashboardApplication.class, TestConfiguration.class})
@WebIntegrationTest
public class SubscriptionControllerIntegrationTest {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private String dashboardBaseUrl;

    @Autowired
    private HttpRequestSigningService signingService;

    private static final String TOKEN = "A-TOKEN";
    private static final ResultDto EXPECTED_SUCCESS_RESULT = successfulResult(SUCCESS_MESSAGE, TOKEN);

    private static final String SUBSCRIPTION_URI = "/subscription/create/notification?resource={resource}&token={token}";

    private static final String SUBSCRIPTION_RESOURCE = EVENT_PATH;

    private static final String EXPECTED_SUCCESS_XML_RESPONSE = String.format("<result xmlns=\"\"><success>%s</success><message>%s</message><accountIdentifier>%s</accountIdentifier></result>",
            EXPECTED_SUCCESS_RESULT.isSuccess(), EXPECTED_SUCCESS_RESULT.getMessage(), EXPECTED_SUCCESS_RESULT.getAccountIdentifier());

    private UriTemplateHttpRequest signedRequest;

    @Before
    public void generateUrls() {
        final URI fullSubscriptionResourceUri = new UriTemplate(getFullyQualifiedUriPattern(dashboardBaseUrl, SUBSCRIPTION_RESOURCE)).expand(TOKEN);
        final URI fullSubscriptionRequestUri = new UriTemplate(
                getFullyQualifiedUriPattern(dashboardBaseUrl, SUBSCRIPTION_URI))
                .expand(fullSubscriptionResourceUri, TOKEN);

        signedRequest = signingService.sign(withUrl(fullSubscriptionRequestUri.toString())
                .method(GET)
                .mediaType(APPLICATION_XML)
                .build());

    }

    @Test
    public void create_subscription_will_return_accepted() throws Exception {
        HttpEntity entity = new HttpEntity(signedRequest.getHeaders());
        ResponseEntity<ResultDto> response = restTemplate.exchange(
                signedRequest.getRequestUri(),
                signedRequest.getHttpMethod(), entity, ResultDto.class);

        assertThat(response.getStatusCode(), is(ACCEPTED));
    }

    @Test
    public void create_subscription_will_return_success_result() throws Exception {
        HttpEntity entity = new HttpEntity(signedRequest.getHeaders());
        ResponseEntity<ResultDto> response = restTemplate.exchange(
                signedRequest.getRequestUri(),
                signedRequest.getHttpMethod(), entity, ResultDto.class);

        assertThat(response.getBody(), is(EXPECTED_SUCCESS_RESULT));
    }

    @Test
    public void create_subscription_will_return_valid_XML() throws Exception {

        HttpEntity entity = new HttpEntity(signedRequest.getHeaders());

        ResponseEntity<String> response = restTemplate.exchange(
                signedRequest.getRequestUri(),
                GET, entity, String.class);

        assertThat(response.getBody(), is(EXPECTED_SUCCESS_XML_RESPONSE));
    }

    @Test
    public void create_subscription_will_fail_when_unsigned() throws Exception {
        UriTemplateHttpRequest unsignedRequest = withUrl(signedRequest.getRequestUrl())
                .method(signedRequest.getHttpMethod())
                .mediaType(signedRequest.getMediaType())
                .build();

        confirmRequestFailsWithStatusCode(unsignedRequest, FORBIDDEN);
    }

    @Test
    public void create_subscription_will_fail_when_signed_with_wrong_key() throws Exception {
        HttpRequestSigningService signingService = new HttpRequestSigningService(
                new OAuthConsumerFactory("CRAP-KEY", "NO-SeCret"));

        UriTemplateHttpRequest poorlySignedRequest = signingService.sign(withUrl(signedRequest.getRequestUrl())
                .method(signedRequest.getHttpMethod())
                .mediaType(signedRequest.getMediaType())
                .build());


        confirmRequestFailsWithStatusCode(poorlySignedRequest, UNAUTHORIZED);
    }

    public void confirmRequestFailsWithStatusCode(UriTemplateHttpRequest request, HttpStatus expectedStatus) {
        HttpEntity entity = new HttpEntity(request.getHeaders());

        try {
            restTemplate.exchange(
                    request.getRequestUri(),
                    request.getHttpMethod(), entity, String.class);
            Assert.fail("Request must get rejected and didn't");
        }catch (HttpClientErrorException clientErrorException) {
            HttpStatus statusCode = clientErrorException.getStatusCode();
            assertThat(statusCode, is(expectedStatus));
        }
    }
}