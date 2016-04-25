package com.solvedbysunrise.appdirect.dao;

import com.solvedbysunrise.appdirect.AppDirectDashboardApplication;
import com.solvedbysunrise.appdirect.config.TestConfiguration;
import com.solvedbysunrise.appdirect.entity.jpa.Event;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

import static com.solvedbysunrise.appdirect.enums.Type.UNKNOWN;
import static java.lang.String.format;
import static java.util.Optional.ofNullable;
import static org.hamcrest.CoreMatchers.is;
import static org.joda.time.DateTime.now;
import static org.junit.Assert.assertThat;

@Rollback
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {AppDirectDashboardApplication.class, TestConfiguration.class})
@IntegrationTest
public class EventDaoIntegrationTest {

    private static final Event EXPECTED_EVENT = buildEvent();

    @Autowired
    private EventDao eventDao;

    @Before
    public void entity_will_be_saved() {
        eventDao.save(EXPECTED_EVENT);
    }

    @Test
    public void entity_will_be_retrieved_from_dao() {
        Event event = ofNullable(eventDao.findOne(EXPECTED_EVENT.getId()))
                        .orElseThrow(
                                () -> new RuntimeException(
                                        format("Entity <%s> not in Database",
                                                EXPECTED_EVENT.toString())));
        assertThat(event, is(EXPECTED_EVENT));
    }

    private static Event buildEvent() {
        Event event = new Event();
        event.setId("MY_ID");
        event.setEvent(UNKNOWN);
        event.setNotificationTime(
                new Timestamp(
                                now()
                                .withTimeAtStartOfDay()
                                .getMillis()
                ));
        return event;
    }
}