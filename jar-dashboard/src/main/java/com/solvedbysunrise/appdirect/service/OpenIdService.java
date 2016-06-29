package com.solvedbysunrise.appdirect.service;

import com.google.common.collect.Lists;
import oauth.signpost.OAuthConsumer;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.stereotype.Service;

import static org.springframework.security.oauth2.common.AuthenticationScheme.form;

@Service
public class OpenIdService {

    public OAuth2ProtectedResourceDetails loadAuth2Details() {
        AuthorizationCodeResourceDetails auth2Details = new AuthorizationCodeResourceDetails();
        //OAuthConsumer consumer = consumerFactory.getInstance();
        auth2Details.setAuthenticationScheme(form);
        auth2Details.setClientAuthenticationScheme(form);
       // auth2Details.setClientId(consumer.getConsumerKey());
        //auth2Details.setClientSecret(consumer.getConsumerSecret());
//        auth2Details.setUserAuthorizationUri("https://accounts.google.com/o/oauth2/auth");
//        auth2Details.setAccessTokenUri("https://www.googleapis.com/oauth2/v3/token");
        auth2Details.setScope(Lists.newArrayList("openid"));
        return auth2Details;
    }
}
