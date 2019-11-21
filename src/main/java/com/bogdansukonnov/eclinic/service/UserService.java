package com.bogdansukonnov.eclinic.service;

import com.bogdansukonnov.eclinic.security.UserPrincipal;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {
    @Transactional
    void addUser(String username, String password);

    String defaultPage(UserPrincipal principal);
}
