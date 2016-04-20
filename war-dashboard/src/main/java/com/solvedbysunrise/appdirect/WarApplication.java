package com.solvedbysunrise.appdirect;

import com.solvedbysunrise.appdirect.config.ProductionConfiguration;
import com.solvedbysunrise.appdirect.config.WarConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class WarApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(new Object[] {
                AppDirectDashboardApplication.class,
                WarConfiguration.class
        });
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(
                new Object[] {
                        WarApplication.class,
                        AppDirectDashboardApplication.class,
                        WarConfiguration.class
                }, args);
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
