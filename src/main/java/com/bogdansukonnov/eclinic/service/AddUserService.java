package com.bogdansukonnov.eclinic.service;

import com.bogdansukonnov.eclinic.dao.AppUserDAO;
import com.bogdansukonnov.eclinic.entity.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AddUserService {

    @NonNull
    private AppUserDAO appUserDAO;

    @NonNull
    PasswordEncoder encoder;

    @Transactional
    public void addUser(String username, String password) {

        AppUser user = new AppUser();
        user.setUsername(username);
        user.setPassword(encoder.encode(password));

        appUserDAO.create(user);
    }

}
