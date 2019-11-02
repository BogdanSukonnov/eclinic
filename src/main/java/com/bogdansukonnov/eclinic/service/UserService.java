package com.bogdansukonnov.eclinic.service;

import com.bogdansukonnov.eclinic.dao.UserDAOOld;
import com.bogdansukonnov.eclinic.entity.AppUser;
import com.bogdansukonnov.eclinic.security.AuthorityType;
import com.bogdansukonnov.eclinic.security.UserPrincipal;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserService {

    private UserDAOOld userDAO;
    private PasswordEncoder encoder;

    @Transactional
    public void addUser(String username, String password) {

        AppUser user = new AppUser();
        user.setUsername(username);
        user.setPassword(encoder.encode(password));

        userDAO.create(user);
    }

    public String defaultPage(UserPrincipal principal) {

        if (principal.hasRole(AuthorityType.ROLE_DOCTOR)
            || principal.hasRole(AuthorityType.ROLE_ADMIN)) {
            return "redirect:/doctor/patients";
        }
        else return "redirect:/nurse/events";
    }

}
