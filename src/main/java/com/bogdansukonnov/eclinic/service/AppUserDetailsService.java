package com.bogdansukonnov.eclinic.service;

import com.bogdansukonnov.eclinic.security.UserPrincipal;
import com.bogdansukonnov.eclinic.dao.UserDAO;
import com.bogdansukonnov.eclinic.entity.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("appUserDetailsService")
public class AppUserDetailsService implements UserDetailsService {

    @NonNull
    @Autowired
    private UserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(String username) {
        AppUser user = userDAO.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new UserPrincipal(user);
    }

}
