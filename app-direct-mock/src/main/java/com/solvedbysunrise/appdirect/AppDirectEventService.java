package com.solvedbysunrise.appdirect;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppDirectEventService {

    private static final String TOKEN_PLACEHOLER = "{TOKEN}";

    private final FileContentReader fileContentReader;

    @Autowired
    public AppDirectEventService(FileContentReader fileContentReader) {
        this.fileContentReader = fileContentReader;
    }

    public String getEventDetail(String token){
        String content = fileContentReader.loadContent("subscription-order-event.json");
        return StringUtils.replace(content,TOKEN_PLACEHOLER, token);
    }
}
