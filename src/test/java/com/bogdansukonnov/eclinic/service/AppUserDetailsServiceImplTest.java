package com.bogdansukonnov.eclinic.service;

import com.bogdansukonnov.eclinic.dao.UserDao;
import com.bogdansukonnov.eclinic.entity.AppUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AppUserDetailsServiceImplTest {

    @Mock
    UserDao userDao;

    private String username = "Alla";

    @Test
    void loadUserByUsername() {

        AppUserDetailsServiceImpl appUserDetailsService = new AppUserDetailsServiceImpl(userDao);

        assertThrows(UsernameNotFoundException.class, () -> appUserDetailsService.loadUserByUsername(username));

        AppUser user = new AppUser();
        user.setUsername(username);

        when(userDao.findByUsername(username)).thenReturn(user);

        UserDetails userDetails = appUserDetailsService.loadUserByUsername(username);

        assertEquals(username, userDetails.getUsername());

    }
}