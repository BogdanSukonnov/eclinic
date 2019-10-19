package com.bogdansukonnov.eclinic.dao;

import com.bogdansukonnov.eclinic.entity.Prescription;
import org.springframework.stereotype.Repository;

@Repository
public class PrescriptionDAO extends AbstractDAO<Prescription> {

    public PrescriptionDAO() {
        setClazz(Prescription.class);
    }

}
