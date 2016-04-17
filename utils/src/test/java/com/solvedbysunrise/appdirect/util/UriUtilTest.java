package com.solvedbysunrise.appdirect.util;

import org.junit.Test;

import static com.solvedbysunrise.appdirect.util.UriUtil.getFullyQualifiedUriPattern;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class UriUtilTest {


    private static final String EXPECTED_URL = "http://localhost:8080/someuri";

    private static final String BASE_URL = "http://localhost:8080";

    private static final String URI = "someuri";

    @Test
    public void getFullyQualifiedUriPattern_will_create_url_when_slashes_are_not_provided() throws Exception {
        assertThat(getFullyQualifiedUriPattern(BASE_URL, URI), is(EXPECTED_URL));
    }

    @Test
    public void getFullyQualifiedUriPattern_will_create_url_when_slashes_are_trailing_url() throws Exception {
        assertThat(getFullyQualifiedUriPattern(BASE_URL+"/", URI), is(EXPECTED_URL));
    }

    @Test
    public void getFullyQualifiedUriPattern_will_create_url_when_slashes_are_leading_uri() throws Exception {
        assertThat(getFullyQualifiedUriPattern(BASE_URL, "/"+URI), is(EXPECTED_URL));
    }

    @Test
    public void getFullyQualifiedUriPattern_will_create_url_when_slashes_are_leading_and_trailing() throws Exception {
        assertThat(getFullyQualifiedUriPattern(BASE_URL+"/", "/"+URI), is(EXPECTED_URL));
    }

}