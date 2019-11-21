package com.bogdansukonnov.eclinic.dao;

import com.bogdansukonnov.eclinic.entity.AppUser;

public interface UserDao extends Dao<AppUser> {

    AppUser findByUsername(String username);

}
