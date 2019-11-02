package com.bogdansukonnov.eclinic.dao;

import com.bogdansukonnov.eclinic.entity.AppUser;
import org.springframework.transaction.annotation.Transactional;

public interface IUserDAO extends IDAO<AppUser> {

    @Transactional(readOnly = true)
    AppUser findByUsername(String username);

}
