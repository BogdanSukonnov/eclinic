package com.bogdansukonnov.eclinic.dao;

import com.bogdansukonnov.eclinic.entity.TimePattern;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TimePatternDAO extends AbstractTableDAO<TimePattern> implements ITableDAO<TimePattern> {

    public TimePatternDAO() {
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

    @Override
    public String getQueryConditions(String search, Long parentId) {
        String conditions = "";
        if (!StringUtils.isBlank(search)) {
            conditions = " where lower(t.name) like lower(:search)";
        }
        return conditions;
    }

}
