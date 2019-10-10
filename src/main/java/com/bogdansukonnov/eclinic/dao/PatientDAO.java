package com.bogdansukonnov.eclinic.dao;

import com.bogdansukonnov.eclinic.entity.Patient;
import org.springframework.stereotype.Repository;

@Repository
public class PatientDAO extends AbstractDAO<Patient> {

    public PatientDAO() {
        setClazz(Patient.class);
    }

}
