package com.solvedbysunrise.appdirect;

import com.google.common.collect.Lists;
import com.solvedbysunrise.appdirect.config.ProductionConfiguration;
import com.solvedbysunrise.appdirect.oauth.OAuthConsumerFactory;
import com.solvedbysunrise.appdirect.oauth.OauthConsumerDetailsService;
import com.solvedbysunrise.appdirect.oauth.SignatureBasedOAuthAuthenticationHandler;
import com.solvedbysunrise.appdirect.oauth2.OpenIDConnectAuthenticationFilter;
import oauth.signpost.OAuthConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth.provider.OAuthProcessingFilterEntryPoint;
import org.springframework.security.oauth.provider.filter.ProtectedResourceProcessingFilter;
import org.springframework.security.oauth.provider.nonce.InMemoryNonceServices;
import org.springframework.security.oauth.provider.nonce.OAuthNonceServices;
import org.springframework.security.oauth.provider.token.InMemoryProviderTokenServices;
import org.springframework.security.oauth.provider.token.OAuthProviderTokenServices;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import static com.solvedbysunrise.appdirect.enums.User.APP_DIRECT;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.security.oauth2.common.AuthenticationScheme.form;

@SpringBootApplication
@EnableConfigurationProperties
@EnableWebSecurity
@EnableOAuth2Client
@EnableAutoConfiguration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class AppDirectDashboardApplication {

    private static final String LOGIN_URL = "login";
    @Autowired
    private OAuthConsumerFactory consumerFactory;

    @Autowired
    private OAuth2ClientContext oAuth2ClientContext;

    @Autowired
    private String oauth2ClientId;

    @Autowired
    private String oauth2ClientSecret;

    public static void main(String[] args) {
		SpringApplication.run(
                new Object[] {AppDirectDashboardApplication.class,
                                ProductionConfiguration.class},
                args);
    }


    @Configuration
    @Order(1)
    public static class RestWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
        private ProtectedResourceProcessingFilter protectedResourceProcessingFilter = new  ProtectedResourceProcessingFilter();
       // private OpenIDConnectAuthenticationFilter openIDConnectAuthenticationFilter = new OpenIDConnectAuthenticationFilter();

        @Autowired
        private OauthConsumerDetailsService oauthConsumerDetailsService;
        @Autowired
        private SignatureBasedOAuthAuthenticationHandler oauthAuthenticationHandler;
        @Autowired
        private OAuthProcessingFilterEntryPoint oauthProcessingFilterEntryPoint;
        @Autowired
        private OAuthProviderTokenServices oauthProviderTokenServices;
        @Autowired
        private OAuthNonceServices oauthNonceServices;
        @Autowired
        private AccessDeniedHandler accessDeniedHandler;

        @Autowired
        private OAuth2ClientContextFilter oAuth2ClientContextFilter;
        @Autowired
        private OpenIDConnectAuthenticationFilter openIDConnectAuthenticationFilter;
        @Autowired
        private OAuthProcessingFilterEntryPoint oauth2ProcessingFilterEntryPoint;

        @Autowired
        private OAuth2ClientContext oAuth2ClientContext;


        @PostConstruct
        public void init() {
            protectedResourceProcessingFilter.setConsumerDetailsService(oauthConsumerDetailsService);
            protectedResourceProcessingFilter.setAuthenticationEntryPoint(oauthProcessingFilterEntryPoint);
            protectedResourceProcessingFilter.setAuthHandler(oauthAuthenticationHandler);
            protectedResourceProcessingFilter.setNonceServices(oauthNonceServices);
            protectedResourceProcessingFilter.setTokenServices(oauthProviderTokenServices);
        }

        protected void configure(HttpSecurity http) throws Exception {

            http.antMatcher("/subscription/**")
                    .addFilterBefore(protectedResourceProcessingFilter, UsernamePasswordAuthenticationFilter.class)
                    .authorizeRequests().anyRequest().hasRole(APP_DIRECT.getAuthority())
            .and().exceptionHandling().accessDeniedHandler(accessDeniedHandler)

            .and().addFilterAfter(protectedResourceProcessingFilter, AbstractPreAuthenticatedProcessingFilter.class)
                    .addFilterAfter(openIDConnectAuthenticationFilter, OAuth2ClientContextFilter.class)
                    .exceptionHandling().authenticationEntryPoint(oauth2ProcessingFilterEntryPoint)
                    .and().authorizeRequests()
                    .antMatchers(GET, "/").permitAll()
                    .antMatchers(GET, "/test").authenticated();
        }
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }



    @Bean(name = "oauthProviderTokenServices")
    public OAuthProviderTokenServices oauthProviderTokenServices() {
        return new InMemoryProviderTokenServices(); // not needed but can't be null
    }

    @Bean(name = "oauthNonceServices")
    public InMemoryNonceServices inMemoryNonceServices() {
        return new InMemoryNonceServices();
    }

    @Bean(name = "accessDeniedHandler")
    public AccessDeniedHandler accessDeniedHandler() {
        return new AccessDeniedHandlerImpl();
    }


    @Bean
    public OAuth2ProtectedResourceDetails auth2Details() {
        AuthorizationCodeResourceDetails auth2Details = new AuthorizationCodeResourceDetails();
        OAuthConsumer consumer = consumerFactory.getInstance();
        auth2Details.setAuthenticationScheme(form);
        auth2Details.setClientAuthenticationScheme(form);
        auth2Details.setClientId(consumer.getConsumerKey());
        auth2Details.setClientSecret(consumer.getConsumerSecret());
        auth2Details.setUserAuthorizationUri("https://accounts.google.com/o/oauth2/auth");
        auth2Details.setAccessTokenUri("https://www.googleapis.com/oauth2/v3/token");
        auth2Details.setScope(Lists.newArrayList("openid"));
        return auth2Details;
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new LoginUrlAuthenticationEntryPoint(LOGIN_URL);
    }

    @Bean(name = "oauth2ProcessingFilterEntryPoint")
    public OAuthProcessingFilterEntryPoint oauth2ProcessingFilterEntryPoint() {
        return new OAuthProcessingFilterEntryPoint();
    }

    @Bean
    public OpenIDConnectAuthenticationFilter openIdConnectAuthenticationFilter() {
        return new OpenIDConnectAuthenticationFilter(LOGIN_URL);
    }

    @Bean
    public OAuth2ClientContextFilter oAuth2ClientContextFilter() {
        return new OAuth2ClientContextFilter();
    }

    @Bean
    @Scope(value = "session", proxyMode = ScopedProxyMode.INTERFACES)
    public OAuth2RestOperations googleOAuth2RestTemplate() {
        return new OAuth2RestTemplate(auth2Details(), oAuth2ClientContext);
    }
}
