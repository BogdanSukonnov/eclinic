package com.bogdansukonnov.eclinic.dao;

import com.bogdansukonnov.eclinic.entity.Patient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

@Repository
public class PatientDAO extends AbstractTableDAO<Patient> implements ITableDAO<Patient> {

    public PatientDAO() {
        setClazz(Patient.class);
    }

    @Override
    public String getQueryConditions(String search) {
        String conditions = "";
        if (!StringUtils.isBlank(search)) {
            conditions = " where (lower(t.fullName) like lower(:search)) or (t.insuranceNumber like :search)";
        }
        return conditions;
    }

}
