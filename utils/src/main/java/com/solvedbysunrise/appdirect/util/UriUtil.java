package com.solvedbysunrise.appdirect.util;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.removeEnd;
import static org.apache.commons.lang3.StringUtils.removeStart;

public final class UriUtil {

    public static String getFullyQualifiedUriPattern(final String baseUrl, final String uri) {
        return format(
                "%s/%s",
                removeEnd(baseUrl,"/"),
                removeStart(uri,"/"));
    }
}

