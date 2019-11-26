package com.bogdansukonnov.eclinic.config;

import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;

@Log4j2
@Component
public class CustomRequestLoggingInterceptor extends HandlerInterceptorAdapter {

    private static void logRequestHeader(HttpServletRequest request, String prefix) {
        val queryString = request.getQueryString();
        if (queryString == null) {
            log.debug("{} {} {}", prefix, request.getMethod(), request.getRequestURI());
        } else {
            log.debug("{} {} {}?{}", prefix, request.getMethod(), request.getRequestURI(), queryString);
        }
        Collections.list(request.getHeaderNames()).forEach(headerName ->
                Collections.list(request.getHeaders(headerName)).forEach(headerValue ->
                        log.debug("{} {}: {}", prefix, headerName, headerValue)));
        log.debug("{}", prefix);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logRequestHeader(request, request.getRemoteAddr() + "|>");
        return true;
    }

}