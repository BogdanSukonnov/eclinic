package com.bogdansukonnov.eclinic.dao;

import com.bogdansukonnov.eclinic.entity.TimePattern;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class TimePatternDaoImpl extends AbstractTableDao<TimePattern> implements TimePatternDao {

    public TimePatternDaoImpl() {
        setClazz(TimePattern.class);
    }

    @Override
    @Transactional(readOnly = true)
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

    @Override
    public String getQueryConditions(String search, Long parentId) {
        String conditions = "";
        if (!StringUtils.isBlank(search)) {
            conditions = " where lower(t.name) like lower(:search)";
        }
        return conditions;
    }

}
