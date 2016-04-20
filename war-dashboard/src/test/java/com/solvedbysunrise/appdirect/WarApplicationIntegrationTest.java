package com.solvedbysunrise.appdirect;

import com.solvedbysunrise.appdirect.config.WarConfiguration;
import com.solvedbysunrise.appdirect.dto.Hello;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import static com.solvedbysunrise.appdirect.util.UriUtil.getFullyQualifiedUriPattern;
import static org.hamcrest.CoreMatchers.is;

@Rollback
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {WarApplication.class, WarConfiguration.class})
@IntegrationTest
public class WarApplicationIntegrationTest {

    @Autowired
    private String dashboardBaseUrl;

    @Autowired
    private RestTemplate restTemplate;

    private static final Hello EXPECTED_WORLD = new Hello();
    {
        EXPECTED_WORLD.setWho("harry");
    }

    @Test
    public void application_will_start_on_tomcat() {
        UriTemplate template = new UriTemplate(getFullyQualifiedUriPattern(dashboardBaseUrl, "/hello"));
        Hello world =  restTemplate.getForEntity(template.expand(), Hello.class).getBody();
        Assert.assertThat(world, is(EXPECTED_WORLD));
    }
}