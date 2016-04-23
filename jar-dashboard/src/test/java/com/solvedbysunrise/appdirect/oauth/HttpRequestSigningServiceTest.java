package com.solvedbysunrise.appdirect.oauth;

import com.solvedbysunrise.appdirect.oauth.exception.OauthRequestSigningFailed;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.http.HttpRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HttpRequestSigningServiceTest {

    @Mock
    private OAuthConsumer mockedConsumer;
    @Mock
    private OAuthConsumerFactory mockedOAuthConsumerFactory;
    @Mock
    private HttpRequest mockedRequest;

    @InjectMocks
    private HttpRequestSigningService requestSigningService;

    @Test(expected = OauthRequestSigningFailed.class)
    public void sign_will_throw_internal_exception_when_signing_fails() throws OAuthCommunicationException, OAuthExpectationFailedException, OAuthMessageSignerException {
        when(mockedOAuthConsumerFactory.getInstance()).thenReturn(mockedConsumer);
        when(mockedConsumer.sign(any(HttpRequest.class))).thenThrow(new OAuthMessageSignerException("mock"));
        requestSigningService.sign(mockedRequest);
    }
}