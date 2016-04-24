package com.solvedbysunrise.appdirect.oauth;

import com.google.common.collect.Lists;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth.common.signature.SharedConsumerSecretImpl;
import org.springframework.security.oauth.common.signature.SignatureSecret;
import org.springframework.security.oauth.provider.BaseConsumerDetails;
import org.springframework.security.oauth.provider.ConsumerDetails;
import org.springframework.security.oauth.provider.ExtraTrustConsumerDetails;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.solvedbysunrise.appdirect.enums.User.APP_DIRECT;

@Service
public class OAuthConsumerFactory {

    private final String oauthConsumerKey;
    private final String oauthConsumerSecret;

    private final static BaseConsumerDetails UNAUTHORIZED = new BaseConsumerDetails();
    {
        UNAUTHORIZED.setAuthorities(Lists.newArrayList());
        UNAUTHORIZED.setConsumerKey("");
        UNAUTHORIZED.setConsumerName("");
        UNAUTHORIZED.setRequiredToObtainAuthenticatedToken(true);
        UNAUTHORIZED.setResourceDescription("");
        UNAUTHORIZED.setSignatureSecret(new SharedConsumerSecretImpl(""));
        UNAUTHORIZED.setResourceName("");
    }

    @Autowired
    public OAuthConsumerFactory(final String oauthConsumerKey, final String oauthConsumerSecret) {
        this.oauthConsumerKey = oauthConsumerKey;
        this.oauthConsumerSecret = oauthConsumerSecret;
    }

    public OAuthConsumer getInstance() {
        return new DefaultOAuthConsumer(oauthConsumerKey, oauthConsumerSecret);
    }

    public ConsumerDetails getConsumerDetailsInstance(String oauthConsumerKey) {
        if(StringUtils.equals(this.oauthConsumerKey, oauthConsumerKey)) {
            return new ExtraTrustConsumerDetails() {
                @Override
                public String getConsumerKey() {
                    return oauthConsumerKey;
                }

                @Override
                public String getConsumerName() {
                    return APP_DIRECT.getName();
                }

                @Override
                public SignatureSecret getSignatureSecret() {
                    return new SharedConsumerSecretImpl(oauthConsumerSecret);
                }

                @Override
                public List<GrantedAuthority> getAuthorities() {
                    return Lists.newArrayList(new SimpleGrantedAuthority(APP_DIRECT.getRole()));
                }

                @Override
                public boolean isRequiredToObtainAuthenticatedToken() {
                    return false;
                }
            };
        }
        return UNAUTHORIZED;
    }
}
