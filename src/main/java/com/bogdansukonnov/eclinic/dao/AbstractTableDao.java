package com.bogdansukonnov.eclinic.dao;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.Query;

import java.util.List;

public abstract class AbstractTableDao<T> extends AbstractDao<T> {

    abstract String getQueryConditions(String search, Long parentId);

    public List<T> getAll(String orderField, String search, int offset, int limit, Long parentId) {
        String queryStr = "from " + getClazz().getName() + " t";
        queryStr += getQueryConditions(search, parentId);
        queryStr += " order by " + orderField;

        Query query = getCurrentSession().createQuery(queryStr);
        if (limit > 0) {
            query.setFirstResult(offset);
            query.setMaxResults(limit);
        }

        setParameters(query, search, parentId);

        return query.list();
    }

    public Long getTotalFiltered(String search, Long parentId) {
        String queryStr = "Select count(t.id) from " + getClazz().getName() + " t";
        queryStr += getQueryConditions(search, parentId);
        Query query = getCurrentSession().createQuery(queryStr);
        setParameters(query, search, parentId);
        return (Long) query.uniqueResult();
    }

    private void setParameters(Query query, String search, Long parentId) {
        if (!StringUtils.isBlank(search)) {
            query.setParameter("search", "%" + search + "%");
        }
        if (parentId != null) {
            query.setParameter("parentId", parentId);
        }
    }

}
