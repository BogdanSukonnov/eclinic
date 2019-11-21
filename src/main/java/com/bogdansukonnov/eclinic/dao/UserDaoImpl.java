package com.bogdansukonnov.eclinic.dao;

import com.bogdansukonnov.eclinic.entity.AppUser;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public class UserDaoImpl extends AbstractDao<AppUser> implements UserDao {

    public UserDaoImpl() {
        setClazz(AppUser.class);
    }

    @Override
    @Transactional(readOnly = true)
    public AppUser findByUsername(String username) {

        String queryText = "FROM AppUser u where u.username=:username";

        Query<AppUser> query = sessionFactory.getCurrentSession().createQuery(queryText, AppUser.class);

        query.setParameter("username", username);

        return query.uniqueResult();
    }

    @Override
    protected String getOrderField() {
        return "fullName";
    }

}
