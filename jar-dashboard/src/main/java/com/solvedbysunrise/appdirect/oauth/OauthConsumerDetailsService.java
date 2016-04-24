package com.solvedbysunrise.appdirect.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth.common.OAuthException;
import org.springframework.security.oauth.provider.ConsumerDetails;
import org.springframework.security.oauth.provider.ConsumerDetailsService;
import org.springframework.stereotype.Service;

@Service
public class OauthConsumerDetailsService implements ConsumerDetailsService {

    private final OAuthConsumerFactory factory;

    @Autowired
    public OauthConsumerDetailsService(OAuthConsumerFactory factory) {
        this.factory = factory;
    }

    @Override
    public ConsumerDetails loadConsumerByConsumerKey(String consumerKey) throws OAuthException {
        return factory.getConsumerDetailsInstance(consumerKey);
    }
}
