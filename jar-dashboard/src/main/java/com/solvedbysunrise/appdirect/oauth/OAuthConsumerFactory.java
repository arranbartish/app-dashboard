package com.solvedbysunrise.appdirect.oauth;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OAuthConsumerFactory {

    private String oauthConsumerKey;
    private String oauthConsumerSecret;

    @Autowired
    public OAuthConsumerFactory(final String oauthConsumerKey, final String oauthConsumerSecret) {
        this.oauthConsumerKey = oauthConsumerKey;
        this.oauthConsumerSecret = oauthConsumerSecret;
    }

    public OAuthConsumer getInstance() {
        return new DefaultOAuthConsumer(oauthConsumerKey, oauthConsumerSecret);
    }
}
