package com.solvedbysunrise.appdirect.oauth;

import org.junit.Before;

public class PreExpandedRequestSigningServiceIntegrationTest extends HttpRequestSigningServiceIntegrationTest {

    @Before
    public void all_behaviours_are_maintained_when_url_is_expand_and_reset() {
        REQUEST.setRequestUrl(REQUEST.getRequestUrl());
    }
}
