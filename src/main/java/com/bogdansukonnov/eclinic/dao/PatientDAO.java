package com.bogdansukonnov.eclinic.dao;

import com.bogdansukonnov.eclinic.entity.Patient;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PatientDAO implements DAO<Patient> {

    @NonNull
    private SessionFactory sessionFactory;

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

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

}
