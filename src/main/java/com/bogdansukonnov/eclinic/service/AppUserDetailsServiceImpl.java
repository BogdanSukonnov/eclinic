package com.bogdansukonnov.eclinic.service;

import com.bogdansukonnov.eclinic.dao.UserDao;
import com.bogdansukonnov.eclinic.entity.AppUser;
import com.bogdansukonnov.eclinic.security.UserPrincipal;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("appUserDetailsService")
@AllArgsConstructor
public class AppUserDetailsServiceImpl implements AppUserDetailsService {

    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) {
        AppUser user = userDao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new UserPrincipal(user);
    }

}
