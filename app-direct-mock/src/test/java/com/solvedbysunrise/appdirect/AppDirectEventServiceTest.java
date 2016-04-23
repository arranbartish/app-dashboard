package com.solvedbysunrise.appdirect;

import org.junit.Test;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

public class AppDirectEventServiceTest {

    private static final String A_RANDOM_TOKEN = "A_RANDOM_TOKEN";
    private final FileContentReader contentReader = new ClasspathFileContentReader();
    private final AppDirectEventService eventService = new AppDirectEventService(contentReader);

    @Test
    public void getEventDetail_will_replace_tokens() {
        String eventDetail = eventService.getEventDetail(A_RANDOM_TOKEN);
        assertThat(eventDetail, containsString(A_RANDOM_TOKEN));
    }
}