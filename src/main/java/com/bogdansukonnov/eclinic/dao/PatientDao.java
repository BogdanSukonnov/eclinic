package com.bogdansukonnov.eclinic.dao;

import com.bogdansukonnov.eclinic.entity.Patient;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PatientDao extends AbstractTableDao<Patient> implements ITableDao<Patient> {

    public PatientDao() {
        setClazz(Patient.class);
    }

    @Override
    public String getQueryConditions(String search, Long parentId) {
        String conditions = "";
        if (!StringUtils.isBlank(search)) {
            conditions = " where (lower(t.fullName) like lower(:search)) or (t.insuranceNumber like :search)";
        }
        return conditions;
    }

    public Optional<Patient> findOne(String fullName) {
        Query query = getCurrentSession().createQuery("from Patient where lower(fullName)=lower(:fullName)");
        query.setParameter("fullName", fullName);
        return query.uniqueResultOptional();
    }

}
