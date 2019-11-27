package com.bogdansukonnov.eclinic.config;

import org.springframework.web.filter.CommonsRequestLoggingFilter;

public class CustomRequestLoggingFilter extends CommonsRequestLoggingFilter {

    public CustomRequestLoggingFilter() {
        super.setIncludeHeaders(true);
        super.setIncludeQueryString(true);
        super.setIncludePayload(true);
        super.setMaxPayloadLength(80000);
    }
}

