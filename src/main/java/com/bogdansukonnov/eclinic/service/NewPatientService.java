package com.bogdansukonnov.eclinic.service;

import com.bogdansukonnov.eclinic.dao.PatientDAO;
import com.bogdansukonnov.eclinic.entity.Patient;
import com.bogdansukonnov.eclinic.entity.PatientStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class NewPatientService {

    private PatientDAO patientDAO;

    @Transactional
    public void addNew(String fullNAme) {
        Patient patient = new Patient();
        patient.setFullName(fullNAme);
        patient.setPatientStatus(PatientStatus.PATIENT);
        // ToDo: insurance, doctor
        patientDAO.save(patient);
    }

}
