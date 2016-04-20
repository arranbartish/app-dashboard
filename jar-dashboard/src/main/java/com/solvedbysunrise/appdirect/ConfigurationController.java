package com.solvedbysunrise.appdirect;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RequestMapping("/configuration")
public class ConfigurationController {

    @Autowired
    private Collection<Pair<String, String>> configuration;

    @RequestMapping(method = GET)
    public Collection<Pair<String, String>> getConfiguration() {
        return configuration;
    }

}
