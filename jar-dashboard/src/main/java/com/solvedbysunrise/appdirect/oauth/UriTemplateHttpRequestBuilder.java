package com.solvedbysunrise.appdirect.oauth;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriTemplate;

import java.util.HashMap;
import java.util.Map;

public class UriTemplateHttpRequestBuilder {


    private String body = "";
    private HttpHeaders headers = new HttpHeaders();

    private MediaType mediaType;
    private final String url;
    private HttpMethod method;
    private Map<String, ?> uriVariables = new HashMap<>();

    private UriTemplateHttpRequestBuilder(final String url) {
        this.url = url;
    }

    public static UriTemplateHttpRequestBuilder withUrl(String url) {
        return new UriTemplateHttpRequestBuilder(url);
    }

    public UriTemplateHttpRequestBuilder body(String body) {
        this.body = body;
        return this;
    }

    public UriTemplateHttpRequestBuilder headers(HttpHeaders headers) {
        this.headers = headers;
        return this;
    }

    public UriTemplateHttpRequestBuilder mediaType(MediaType mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    public UriTemplateHttpRequestBuilder method(HttpMethod method) {
        this.method = method;
        return this;
    }

    public UriTemplateHttpRequestBuilder uriVariables(Map<String, ?> uriVariables) {
        this.uriVariables = uriVariables;
        return this;
    }

    public UriTemplateHttpRequest build() {
        return new UriTemplateHttpRequest(
                url,
                body,
                mediaType,
                method,
                uriVariables);
    }
}
