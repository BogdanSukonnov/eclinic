package com.bogdansukonnov.eclinic.dao;

import com.bogdansukonnov.eclinic.entity.Prescription;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PrescriptionDAO extends AbstractDAO<Prescription> {

    public PrescriptionDAO() {
        setClazz(Prescription.class);
    }

    public List<Prescription> getAllByPatient(Long patientId) {
        String queryStr = "from Prescription p where p.patient.id=:patientId order by " + getOrderField();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("patientId", patientId);
        return query.list();
    }



}
