package com.bogdansukonnov.eclinic.service;

import com.bogdansukonnov.eclinic.converter.PrescriptionConverter;
import com.bogdansukonnov.eclinic.dao.*;
import com.bogdansukonnov.eclinic.dto.PrescriptionDTO;
import com.bogdansukonnov.eclinic.entity.*;
import com.bogdansukonnov.eclinic.security.UserGetter;
import com.bogdansukonnov.eclinic.security.UserPrincipal;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PrescriptionService {

    private PrescriptionDAO prescriptionDAO;

    private PrescriptionConverter converter;

    private TreatmentDAO treatmentDAO;

    private TimePatternDAO timePatternDAO;

    private PatientDAO patientDAO;

    private UserGetter userGetter;

    @Transactional(readOnly = true)
    public List<PrescriptionDTO> getAll(SortBy sortBy) {
        return prescriptionDAO.getAll(sortBy).stream()
                .map(prescription -> converter.toDTO(prescription))
                .collect(Collectors.toList());
    }

    @Transactional
    public void save(SaveType saveType, PrescriptionDTO prescriptionDTO) {

        Treatment treatment = treatmentDAO.findOne(prescriptionDTO.getTreatmentId());
        TimePattern timePattern = timePatternDAO.findOne(prescriptionDTO.getPatternId());
        Patient patient = patientDAO.findOne(prescriptionDTO.getPatientId());

        Prescription prescription;
        if (saveType == SaveType.CREATE) {
            prescription = new Prescription();
        }
        else {
            prescription = prescriptionDAO.findOne(prescriptionDTO.getId());
        }

        prescription = converter.toEntity(prescription, prescriptionDTO, patient, treatment, timePattern);

        //set current user as doctor
        prescription.setDoctor(userGetter.getCurrentUser());

        //only medicine could have a dosage
        if (prescription.getTreatment().getType() != TreatmentType.Medicine) {
            prescription.setDosage("");
        }

        if (saveType == SaveType.CREATE) {
            prescriptionDAO.create(prescription);
        }
        else {
            prescriptionDAO.update(prescription);
        }
    }

    @Transactional(readOnly = true)
    public PrescriptionDTO getOne(Long id) {
        Prescription prescription = prescriptionDAO.findOne(id);
        return converter.toDTO(prescription);
    }

}
