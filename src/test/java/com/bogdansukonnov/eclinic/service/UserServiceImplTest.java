package com.bogdansukonnov.eclinic.service;

import com.bogdansukonnov.eclinic.dao.UserDao;
import com.bogdansukonnov.eclinic.security.AuthorityType;
import com.bogdansukonnov.eclinic.security.UserPrincipal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class UserServiceImplTest {

    @Mock
    private UserDao userDao;
    @Mock
    private PasswordEncoder encoder;

    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userDao, encoder);
    }

    @Test
    void addUser() {
        String username = "Sara Connor";
        String password = "pass";
        String encodedPassword = "abracadabra";
        when(encoder.encode(password)).thenReturn(encodedPassword);

        userService.addUser(username, password);

        verify(userDao).create(any());
    }

    @Test
    void defaultPage() {
        UserPrincipal principal = mock(UserPrincipal.class);

        when(principal.hasRole(AuthorityType.ROLE_DOCTOR)).thenReturn(true);

        assertEquals("redirect:/doctor/patients", userService.defaultPage(principal));

        when(principal.hasRole(AuthorityType.ROLE_DOCTOR)).thenReturn(false);

        assertEquals("redirect:/nurse/events", userService.defaultPage(principal));
    }
}