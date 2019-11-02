package com.bogdansukonnov.eclinic.dao;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.Query;

import java.util.List;

public abstract class AbstractTableDAO<T> extends AbstractDAO<T> {

    protected abstract String getQueryConditions(String search);

    @SuppressWarnings("unchecked")
    public List<T> getAll(String orderField, String search, int offset, int limit) {
        String queryStr = "from " + getClazz().getName() + " t";
        queryStr += getQueryConditions(search);
        queryStr += " order by " + orderField;

        Query query = getCurrentSession().createQuery(queryStr)
                .setFirstResult(offset)
                .setMaxResults(limit);

        setParameters(query, search);

        return query.list();
    }

    public Long getTotalFiltered(String search) {
        String queryStr = "Select count(t.id) from " + getClazz().getName() + " t";
        queryStr += getQueryConditions(search);
        Query query = getCurrentSession().createQuery(queryStr);
        setParameters(query, search);
        return (Long) query.uniqueResult();
    }

    private void setParameters(Query query, String search) {
        if (!StringUtils.isBlank(search)) {
            query.setParameter("search", "%" + search + "%");
        }
    }

}
