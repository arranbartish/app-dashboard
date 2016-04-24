package de.hybris.pipeline;

import com.solvedbysunrise.appdirect.ClasspathFileContentReader;
import com.solvedbysunrise.appdirect.exception.ContentUnreadable;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.junit.Assert.assertThat;

public class ClasspathFileContentReaderTest {

    private final ClasspathFileContentReader contentReader = new ClasspathFileContentReader();

    private static final String EXPECTED_FILE_CONTENT = "{ \"name\" : \"test\" }";

    @Test(expected = ContentUnreadable.class)
    public void loadContent_will_throw_exception_when_file_does_not_exist() {
        contentReader.loadContent("doesnotexist");
    }

    @Test
    public void loadContent_will_load_content_for_existing_file() {
        String content = contentReader.loadContent("mocks/dashboard/test.json");
        assertThat(content, is(EXPECTED_FILE_CONTENT));
    }


    @Test
    public void loadContent_will_load_content_from_real_mock_file() {
        String content = contentReader.loadContent("subscription-order-event.json");
        assertThat( content, is( not( isEmptyOrNullString() ) ) );
    }
}