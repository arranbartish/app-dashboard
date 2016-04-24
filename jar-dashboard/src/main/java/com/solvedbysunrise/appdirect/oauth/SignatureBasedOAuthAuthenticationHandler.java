package com.solvedbysunrise.appdirect.oauth;

import com.google.common.collect.Sets;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth.provider.ConsumerAuthentication;
import org.springframework.security.oauth.provider.ConsumerCredentials;
import org.springframework.security.oauth.provider.OAuthAuthenticationHandler;
import org.springframework.security.oauth.provider.token.OAuthAccessProviderToken;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Collection;

@Component
public class SignatureBasedOAuthAuthenticationHandler implements OAuthAuthenticationHandler {

    @Override
    public Authentication createAuthentication(HttpServletRequest request,
                                               ConsumerAuthentication authentication,
                                               OAuthAccessProviderToken authToken) {
        Collection<GrantedAuthority> authorities = Sets.newHashSet(authentication.getAuthorities());

        SignedOAuthPrincipal appDirect = new SignedOAuthPrincipal(authentication.getName(),
                authorities,
                authentication.getConsumerCredentials());

        return new UsernamePasswordAuthenticationToken(appDirect,
                authentication.getCredentials(),
                authorities);
    }

    public static class SignedOAuthPrincipal extends ConsumerCredentials implements Principal {
        public String name;
        public Collection<GrantedAuthority> authorities;

        public SignedOAuthPrincipal(String name, Collection<GrantedAuthority> authorities, ConsumerCredentials credentials) {
            super(credentials.getConsumerKey(),
                    credentials.getSignature(),
                    credentials.getSignatureMethod(),
                    credentials.getSignatureBaseString(),
                    credentials.getToken());
            this.name = name;
            this.authorities = authorities;
        }

        @Override
        public String getName() {
            return name;
        }

        public Collection<? extends GrantedAuthority> getAuthorities() {
            return authorities;
        }
    }
}
