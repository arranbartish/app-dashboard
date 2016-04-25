package com.solvedbysunrise.appdirect;

import com.solvedbysunrise.appdirect.config.ProductionConfiguration;
import com.solvedbysunrise.appdirect.config.WarConfiguration;
import com.solvedbysunrise.appdirect.oauth.OauthConsumerDetailsService;
import com.solvedbysunrise.appdirect.oauth.SignatureBasedOAuthAuthenticationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth.provider.OAuthProcessingFilterEntryPoint;
import org.springframework.security.oauth.provider.filter.ProtectedResourceProcessingFilter;
import org.springframework.security.oauth.provider.nonce.OAuthNonceServices;
import org.springframework.security.oauth.provider.token.OAuthProviderTokenServices;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.PostConstruct;

import static com.solvedbysunrise.appdirect.enums.User.APP_DIRECT;

@SpringBootApplication
public class WarApplication extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("/static/");
        super.addResourceHandlers(registry);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(
                new Object[] {
                        WarApplication.class,
                        AppDirectDashboardApplication.class,
                        WarConfiguration.class
                }, args);
    }



    @Configuration
    @Order(1)
    public static class WebMvcConfigurationAdapter extends SpringBootServletInitializer {

        @Override
        protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
            return application.sources(new Object[] {
                    AppDirectDashboardApplication.class,
                    WarConfiguration.class
            });
        }

//        @Override
//        public void addResourceHandlers(ResourceHandlerRegistry registry) {
//            registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
//        }
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
