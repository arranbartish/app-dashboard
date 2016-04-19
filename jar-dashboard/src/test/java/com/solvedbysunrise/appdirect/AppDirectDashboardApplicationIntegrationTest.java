package com.solvedbysunrise.appdirect;

import com.solvedbysunrise.appdirect.config.TestConfiguration;
import com.solvedbysunrise.appdirect.dto.Hello;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
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
@SpringApplicationConfiguration(classes = {AppDirectDashboardApplication.class, TestConfiguration.class})
@WebIntegrationTest
public class AppDirectDashboardApplicationIntegrationTest {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private String dashboardBaseUrl;

    private static final Hello EXPECTED_WORLD = new Hello();
    {
        EXPECTED_WORLD.setWho("harry");
    }

	@Test
	public void application_will_start() {
		UriTemplate template = new UriTemplate(getFullyQualifiedUriPattern(dashboardBaseUrl, "/hello"));
        Hello world =  restTemplate.getForEntity(template.expand(), Hello.class).getBody();
		Assert.assertThat(world, is(EXPECTED_WORLD));
	}

}
