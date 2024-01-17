package org.sayres.springmvc.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * @author Aliaksei Karabelnikau
 */
public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {

    public SecurityWebApplicationInitializer() {
        super(SecurityConfig.class);
    }
}
