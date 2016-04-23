package com.solvedbysunrise.appdirect.oauth;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.solvedbysunrise.appdirect.oauth.UriTemplateHttpRequestBuilder.withUrl;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;

@RunWith(MockitoJUnitRunner.class)
public class HttpRequestSigningServiceIntegrationTest {

    private static final String A_KEY = "a-key";
    private static final String A_SECRET = "shhhhhh...Secret";

    private OAuthConsumerFactory consumerFactory = new OAuthConsumerFactory(A_KEY, A_SECRET);
    private HttpRequestSigningService requestSigningService = new HttpRequestSigningService(consumerFactory);

    private static final String URI_TEMPLATE = "http://www.example.com/contect?something={value}";
    private static final String EXPECTED_URI = "http://www.example.com/contect?something=real";
    private static final Map<String, String> VARIABLES = new HashMap<>();
    {
        VARIABLES.put("value", "real");
    }

    private static final String BODY = "The Body";
    static final UriTemplateHttpRequest REQUEST = withUrl(URI_TEMPLATE)
                                                .uriVariables(VARIABLES)
                                                .method(GET)
                                                .mediaType(APPLICATION_JSON_UTF8)
                                                .build();

    @Test
    public void sign_will_expand_uri_template_paramaters() throws Exception {

        UriTemplateHttpRequest signedRequest = requestSigningService.sign(REQUEST);

        assertThat(signedRequest.getRequestUrl(), is(EXPECTED_URI));
    }

    @Test
    public void sign_will_add_required_headers_header() throws Exception {

        UriTemplateHttpRequest signedRequest = requestSigningService.sign(REQUEST);

        Set<String> headers = signedRequest.getAllHeaders().keySet();
        assertThat(headers, contains("Content-Type","Authorization"));
    }

    @Test
    public void sign_will_maintain_method() {
        UriTemplateHttpRequest signedRequest = requestSigningService.sign(REQUEST);

        assertThat(signedRequest.getMethod(), is(REQUEST.getMethod()));
    }

    @Test
    public void sign_will_maintain_the_body() throws IOException {
        UriTemplateHttpRequest signedRequest = requestSigningService.sign(REQUEST);

        assertThat(IOUtils.toString(signedRequest.getMessagePayload()),
                is(IOUtils.toString(REQUEST.getMessagePayload())));
    }
}