package com.solvedbysunrise.appdirect;

import com.solvedbysunrise.appdirect.exception.ContentUnreadable;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

import static java.lang.String.format;
import static org.apache.commons.lang3.CharEncoding.UTF_8;

@Service
public class ClasspathFileContentReader implements FileContentReader {

    private PathMatchingResourcePatternResolver fileResolver = new PathMatchingResourcePatternResolver();

    public String loadContent(final String filepath) {
        try {
            Resource resource = getClasspathResourceHandle(filepath);
            if (resource == null || !resource.exists()) {
                throw new FileNotFoundException("File does not exist");
            }

            return getResourceContent(resource);
        } catch (Exception e) {
            throw new ContentUnreadable(format("Failed to load content for [%s]", filepath), e);
        }
    }

    private Resource getClasspathResourceHandle(final String path) throws Exception {
        return  fileResolver.getResourceLoader().getResource("classpath:"+path);
    }

    private String getResourceContent(final Resource resource) throws Exception {
        StringBuilder content = new StringBuilder();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), UTF_8))) {
            for(String line; (line = reader.readLine()) != null; ){
                content.append(line);
            }
        }
        return content.toString();
    }
}
