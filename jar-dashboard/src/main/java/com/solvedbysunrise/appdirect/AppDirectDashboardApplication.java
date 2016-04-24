package com.solvedbysunrise.appdirect;

import com.solvedbysunrise.appdirect.config.ProductionConfiguration;
import com.solvedbysunrise.appdirect.enums.User;
import com.solvedbysunrise.appdirect.oauth.OauthConsumerDetailsService;
import com.solvedbysunrise.appdirect.oauth.SignatureBasedOAuthAuthenticationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.oauth.provider.OAuthAuthenticationHandler;
import org.springframework.security.oauth.provider.OAuthProcessingFilterEntryPoint;
import org.springframework.security.oauth.provider.filter.ProtectedResourceProcessingFilter;
import org.springframework.security.oauth.provider.nonce.InMemoryNonceServices;
import org.springframework.security.oauth.provider.nonce.OAuthNonceServices;
import org.springframework.security.oauth.provider.token.InMemoryProviderTokenServices;
import org.springframework.security.oauth.provider.token.OAuthProviderTokenServices;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

import static com.solvedbysunrise.appdirect.enums.User.APP_DIRECT;

@SpringBootApplication
@EnableConfigurationProperties
@EnableWebSecurity
@EnableAutoConfiguration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class AppDirectDashboardApplication {



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
            .and().exceptionHandling().accessDeniedHandler(accessDeniedHandler);
        }
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean(name = "oauthProcessingFilterEntryPoint")
    public OAuthProcessingFilterEntryPoint oauthAuthenticationEntryPoint() {
        return new OAuthProcessingFilterEntryPoint();
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
}
