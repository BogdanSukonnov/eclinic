package com.bogdansukonnov.eclinic.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AppUserDetailsService extends UserDetailsService {

    @Override
    UserDetails loadUserByUsername(String username);

}
