package com.bogdansukonnov.eclinic.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 *
 * Registers the springSecurityFilterChain with the war
 *
 */
public class SecurityInitializer extends AbstractSecurityWebApplicationInitializer {

    public SecurityInitializer() {
    }
}
