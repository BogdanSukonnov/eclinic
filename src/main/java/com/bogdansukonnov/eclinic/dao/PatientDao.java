package com.bogdansukonnov.eclinic.dao;

import com.bogdansukonnov.eclinic.entity.Patient;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PatientDao implements Dao<Patient> {

    @Autowired
    private SessionFactory sessionFactory;

    public PatientDao() {
    }

    @Override
    public Optional<Patient> get(long id) {
        return Optional.empty();
    }

    @Override
    public List<Patient> getAll() {
        return getCurrentSession().createQuery("from " + Patient.class.getName()).list();
    }

    @Override
    public void save(Patient patient) {
        Session session = getCurrentSession();
        session.saveOrUpdate(patient);
    }

    @Override
    public void update(Patient patient, String[] params) {

    }

    @Override
    public void delete(Patient patient) {

    }

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

}
