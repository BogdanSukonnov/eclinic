package com.bogdansukonnov.eclinic.service;

import com.bogdansukonnov.eclinic.converter.PrescriptionConverter;
import com.bogdansukonnov.eclinic.dao.*;
import com.bogdansukonnov.eclinic.dto.PrescriptionDTO;
import com.bogdansukonnov.eclinic.dto.PrescriptionsTableDTO;
import com.bogdansukonnov.eclinic.entity.*;
import com.bogdansukonnov.eclinic.security.UserGetter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
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

        Treatment treatment = treatmentDAO.findOne(prescriptionDTO.getTreatment().getId());
        TimePattern timePattern = timePatternDAO.findOne(prescriptionDTO.getTimePattern().getId());
        Patient patient = patientDAO.findOne(prescriptionDTO.getPatient().getId());

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

    @Transactional(readOnly = true)
    public PrescriptionsTableDTO getTableByPatient(Long patientId, Map<String, String> data) {

        List<Prescription> allByPatient = prescriptionDAO.getAllByPatient(patientId);

        List<PrescriptionDTO> list = allByPatient.stream()
                .map(prescription -> converter.toDTO(prescription))
                .collect(Collectors.toList());

        PrescriptionsTableDTO tableDTO = PrescriptionsTableDTO.builder()
                .data(list)
                .draw(Integer.parseInt(data.get("draw")))
                .recordsFiltered(list.size())
                .recordsTotal(list.size())
                .build();
        tableDTO.setData(list);

        return tableDTO;
    }

}
