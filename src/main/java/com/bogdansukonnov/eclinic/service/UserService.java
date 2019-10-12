package com.bogdansukonnov.eclinic.service;

import com.bogdansukonnov.eclinic.dao.UserDAO;
import com.bogdansukonnov.eclinic.entity.AppUser;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserService {

    private UserDAO userDAO;

    private PasswordEncoder encoder;

    @Transactional
    public void addUser(String username, String password) {

        AppUser user = new AppUser();
        user.setUsername(username);
        user.setPassword(encoder.encode(password));

        userDAO.create(user);
    }

}
