package com.bogdansukonnov.eclinic.dao;

import com.bogdansukonnov.eclinic.entity.TimePattern;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TimePatternDAOOld extends OldAbstractDAO<TimePattern> {

    public TimePatternDAOOld() {
        setClazz(TimePattern.class);
    }

    public List<TimePattern> getAll(String search) {
        boolean needSearch = search != null && !search.isEmpty();
        String queryStr = "from TimePattern t";
        if (needSearch) {
            queryStr += " where lower(t.name) like lower(:search)";
        }
        queryStr += "  order by " + getOrderField();
        Query query = getCurrentSession().createQuery(queryStr);
        if (needSearch) {
            query.setParameter("search", "%" + search + "%");
        }
        return query.list();
    }

    @Override
    protected String getOrderField() {
        return "name";
    }

}
