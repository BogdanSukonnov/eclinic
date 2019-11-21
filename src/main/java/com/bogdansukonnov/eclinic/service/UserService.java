package com.bogdansukonnov.eclinic.service;

import com.bogdansukonnov.eclinic.security.UserPrincipal;

public interface UserService {

    void addUser(String username, String password);

    String defaultPage(UserPrincipal principal);
}
