package com.bogdansukonnov.eclinic.controller;

import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Log4j2
@Component
public class CustomRequestLoggingInterceptor extends HandlerInterceptorAdapter {
    private static final List<MediaType> VISIBLE_TYPES = Arrays.asList(
            MediaType.valueOf("text/*"),
            MediaType.APPLICATION_FORM_URLENCODED,
            MediaType.APPLICATION_JSON,
            MediaType.APPLICATION_XML,
            MediaType.valueOf("application/*+json"),
            MediaType.valueOf("application/*+xml"),
            MediaType.MULTIPART_FORM_DATA
    );

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