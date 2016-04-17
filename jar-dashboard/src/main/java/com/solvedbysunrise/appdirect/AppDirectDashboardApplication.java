package com.solvedbysunrise.appdirect;

import com.solvedbysunrise.appdirect.config.ProductionConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableConfigurationProperties
public class AppDirectDashboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(
                new Object[] {AppDirectDashboardApplication.class,
                                ProductionConfiguration.class},
                args);
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
