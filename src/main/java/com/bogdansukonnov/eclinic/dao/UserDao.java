package com.bogdansukonnov.eclinic.dao;

import com.bogdansukonnov.eclinic.entity.AppUser;
import org.springframework.transaction.annotation.Transactional;

public interface UserDao extends Dao<AppUser> {

    @Transactional(readOnly = true)
    AppUser findByUsername(String username);

}
