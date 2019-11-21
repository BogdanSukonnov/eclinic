package com.bogdansukonnov.eclinic.security;

import com.bogdansukonnov.eclinic.entity.AppUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityContextAdapter {

    public AppUser getCurrentUser() {
        return ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAppUser();
    }

}
