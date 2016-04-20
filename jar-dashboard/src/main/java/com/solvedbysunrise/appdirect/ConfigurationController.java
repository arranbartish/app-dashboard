package com.solvedbysunrise.appdirect;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RequestMapping("/configuration")
@RestController
public class ConfigurationController {

    @Autowired
    private String databaseUrl;

    @RequestMapping(method = GET)
    public Pair<String, String> getConfiguration() {
        return Pair.of("database",databaseUrl);
    }

}
