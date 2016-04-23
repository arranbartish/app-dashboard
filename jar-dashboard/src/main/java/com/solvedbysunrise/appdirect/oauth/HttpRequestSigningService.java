package com.solvedbysunrise.appdirect.oauth;

import com.solvedbysunrise.appdirect.oauth.exception.OauthRequestSigningFailed;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HttpRequestSigningService {

    private final OAuthConsumerFactory authConsumerFactory;

    @Autowired
    public HttpRequestSigningService(OAuthConsumerFactory authConsumerFactory) {
        this.authConsumerFactory = authConsumerFactory;
    }

    public <T extends HttpRequest>T sign (T requestToSign) {
        final OAuthConsumer consumer = authConsumerFactory.getInstance();

        try {
            consumer.sign(requestToSign);
        } catch (OAuthMessageSignerException
                | OAuthExpectationFailedException
                | OAuthCommunicationException e) {
            throw new OauthRequestSigningFailed("Failed to sign the resourceUri request", e);
        }
        return requestToSign;
    }
}
