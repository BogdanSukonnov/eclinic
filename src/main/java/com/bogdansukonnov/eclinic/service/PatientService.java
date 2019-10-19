package com.bogdansukonnov.eclinic.service;

import com.bogdansukonnov.eclinic.converter.PatientConverter;
import com.bogdansukonnov.eclinic.dao.PatientDAO;
import com.bogdansukonnov.eclinic.dto.PatientDTO;
import com.bogdansukonnov.eclinic.entity.Patient;
import com.bogdansukonnov.eclinic.entity.PatientStatus;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PatientService {

    private PatientDAO patientDAO;

    private PatientConverter patientConverter;

    @Transactional(readOnly = true)
    public List<PatientDTO> getAll() {
        return patientDAO.getAll().stream()
                .map(patient -> patientConverter.toDTO(patient))
                .collect(Collectors.toList());
    }

    @Transactional
    public void addNew(String fullNAme) {
        Patient patient = new Patient();
        patient.setFullName(fullNAme);
        patient.setPatientStatus(PatientStatus.PATIENT);
        // ToDo: insurance, doctor
        patientDAO.create(patient);
    }

    @Transactional(readOnly = true)
    public PatientDTO getOne(Long id) {
        Patient patient = patientDAO.findOne(id);
        return patientConverter.toDTO(patient);
    }

}
