package com.bogdansukonnov.eclinic.dao;

import com.bogdansukonnov.eclinic.entity.Treatment;
import com.bogdansukonnov.eclinic.entity.TreatmentType;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class TreatmentDaoImpl extends AbstractTableDao<Treatment> implements TreatmentDao {

    public TreatmentDaoImpl() {
        setClazz(Treatment.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Treatment> getAll(TreatmentType treatmentType, String search) {
        boolean needSearch = search != null && !search.isEmpty();
        String queryStr = "from Treatment t where t.type=:treatmentType";
        if (needSearch) {
            queryStr += " and lower(t.name) like lower(:search)";
        }
        queryStr += "  order by " + getOrderField();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("treatmentType", treatmentType);
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
