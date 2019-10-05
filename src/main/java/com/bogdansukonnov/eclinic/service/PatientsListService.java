package com.bogdansukonnov.eclinic.service;

import com.bogdansukonnov.eclinic.dao.PatientDao;
import com.bogdansukonnov.eclinic.entity.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PatientsListService {

    private PatientDao patientDao;

    @Autowired
    public PatientsListService(PatientDao patientDao) {
        this.patientDao = patientDao;
    }

    public List<Patient> getAll() {
        return patientDao.getAll();
    }

    public void save(Patient patient) {
        patientDao.save(patient);
    }
}
