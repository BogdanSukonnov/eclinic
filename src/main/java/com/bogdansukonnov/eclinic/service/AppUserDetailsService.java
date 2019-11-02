package com.bogdansukonnov.eclinic.service;

import com.bogdansukonnov.eclinic.dao.IUserDAO;
import com.bogdansukonnov.eclinic.entity.AppUser;
import com.bogdansukonnov.eclinic.security.UserPrincipal;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("appUserDetailsService")
@AllArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

    private IUserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(String username) {
        AppUser user = userDAO.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new UserPrincipal(user);
    }

}
