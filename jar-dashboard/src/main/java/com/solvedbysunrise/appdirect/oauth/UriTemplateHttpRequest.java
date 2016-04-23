package com.solvedbysunrise.appdirect.oauth;

import oauth.signpost.http.HttpRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Map;

import static org.apache.commons.io.IOUtils.toInputStream;
import static org.apache.commons.lang3.CharEncoding.UTF_8;

public class UriTemplateHttpRequest implements HttpRequest {

    private String body;
    private final HttpHeaders headers = new HttpHeaders();

    private final MediaType mediaType;
    private final HttpMethod method;
    private UriTemplate uriTemplate;
    private final Map<String, ?> uriVariables;

    public UriTemplateHttpRequest(final String url,
                                  final String body,
                                  final MediaType mediaType,
                                  final HttpMethod method,
                                  final Map<String, ?> uriVariables){
        this.body = body;
        this.mediaType = mediaType;
        this.method = method;
        this.uriVariables = uriVariables;
        uriTemplate = new UriTemplate(url);
        headers.setContentType(mediaType);
    }

    @Override
    public String getMethod() {
        return getHttpMethod().name();
    }

    public HttpMethod getHttpMethod() {
        return method;
    }

    @Override
    public String getRequestUrl() {
        return getRequestUri().toString();
    }

    public URI getRequestUri() {
        return uriTemplate.expand(uriVariables);
    }

    @Override
    public void setRequestUrl(String url) {
        uriTemplate = new UriTemplate(url);
    }

    @Override
    public void setHeader(String name, String value) {
        headers.add(name, value);
    }

    @Override
    public String getHeader(String name) {
        return headers.getFirst(name);
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    @Override
    public Map<String, String> getAllHeaders() {
        return getHeaders().toSingleValueMap();
    }

    @Override
    public InputStream getMessagePayload() throws IOException {
        return toInputStream(body, UTF_8);
    }

    @Override
    public String getContentType() {
        return mediaType.getType();
    }

    @Override
    public Object unwrap() {
        return getRequestUrl();
    }
}
