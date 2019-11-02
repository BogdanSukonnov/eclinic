package com.bogdansukonnov.eclinic.dao;

import com.bogdansukonnov.eclinic.entity.Prescription;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PrescriptionDAOOld extends OldAbstractDAO<Prescription> {

    public PrescriptionDAOOld() {
        setClazz(Prescription.class);
    }

    public List<Prescription> getAllByPatient(Long patientId) {
        String queryStr = "from Prescription p where p.patient.id=:patientId order by " + getOrderField();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("patientId", patientId);
        return query.list();
    }

    private String addSearchToQuery(String queryStr, String search) {
        if (!StringUtils.isBlank(search)) {
            queryStr += " where lower(t.doctor.fullName) like lower(:search)" +
                    " or lower(t.treatment.name) like lower(:search)";
        }
        return queryStr;
    }

    public List<Prescription> getAll(String orderField, String search, int offset, int limit) {
        String queryStr = "from Prescription t";
        addSearchToQuery(queryStr, search);
        queryStr += " order by " + orderField;

        Query query = getCurrentSession().createQuery(queryStr)
                .setFirstResult(offset)
                .setMaxResults(limit);

        if (!StringUtils.isBlank(search)) {
            query.setParameter("search", "%" + search + "%");
        }

        return query.list();
    }

    public Long getTotalFiltered(String search) {
        String queryStr = "Select count(t.id) from Prescription t";
        addSearchToQuery(queryStr, search);
        Query query = getCurrentSession().createQuery(queryStr);
        if (!StringUtils.isBlank(search)) {
            query.setParameter("search", "%" + search + "%");
        }
        return (Long) query.uniqueResult();
    }


}
