package com.bogdansukonnov.eclinic.dao;

import com.bogdansukonnov.eclinic.entity.Patient;
import com.bogdansukonnov.eclinic.entity.Prescription;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PatientDAO extends AbstractDAO<Patient> {

    public PatientDAO() {
        setClazz(Patient.class);
    }

    public List<Patient> getAll(String orderField, String search, int offset, int limit) {
        String queryStr = "from Patient t ";;
        if (!StringUtils.isBlank(search)) {
            queryStr += "where lower(t.fullName) like lower(:search)";
        }
        queryStr += " order by " + orderField;

        Query query = getCurrentSession().createQuery(queryStr)
                .setFirstResult(offset)
                .setMaxResults(limit);

        if (!StringUtils.isBlank(search)) {
            query.setParameter("search", "%" + search + "%");
        }

        return query.list();
    }

    public Long getCount(String search) {
        String queryStr = "Select count (t.id) from Patient t";;
        if (!StringUtils.isBlank(search)) {
            queryStr += " where lower(t.fullName) like lower(:search)";
        }
        Query query = getCurrentSession().createQuery(queryStr);
        if (!StringUtils.isBlank(search)) {
            query.setParameter("search", "%" + search + "%");
        }
        return (Long) query.uniqueResult();
    }

}
