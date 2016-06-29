package com.solvedbysunrise.appdirect.controller;

import com.google.common.collect.Maps;
import com.solvedbysunrise.appdirect.util.UriUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriTemplate;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.springframework.web.servlet.view.UrlBasedViewResolver.REDIRECT_URL_PREFIX;
import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

@Controller
public class OpenIdController {

    private final String dashboardBaseUrl;

    @Autowired
    public OpenIdController(final String dashboardBaseUrl) {
        this.dashboardBaseUrl = dashboardBaseUrl;
    }

    @RequestMapping(value = "/login")
    public String login(@RequestParam String openId, String accountId) {
        UriTemplate uriTemplate = new UriTemplate(UriUtil.getFullyQualifiedUriPattern(dashboardBaseUrl, "authorized?accountId={accountId}"));

        return REDIRECT_URL_PREFIX+fromHttpUrl(openId)
                .queryParam("client_id", accountId)
                .queryParam("redirect_uri", uriTemplate.expand(accountId).toString())
                .queryParam("response_type", "code")
                .queryParam("scope", "openid")
                .queryParam("state", "zqSpLZ")
                .build().toUriString();
    }
}
