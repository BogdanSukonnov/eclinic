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
    private EventService eventService;

    @Transactional(readOnly = true)
    public List<PrescriptionDTO> getAll(SortBy sortBy) {
        return prescriptionDAO.getAll(sortBy).stream()
                .map(prescription -> converter.toDTO(prescription))
                .collect(Collectors.toList());
    }

    @Transactional
    public void save(SaveType saveType, PrescriptionDTO prescriptionDTO) {

        Treatment treatment = treatmentDAO.findOne(prescriptionDTO.getTreatmentId());
        TimePattern timePattern = timePatternDAO.findOne(prescriptionDTO.getTimePatternId());
        Patient patient = patientDAO.findOne(prescriptionDTO.getPatientId());

        boolean isEventsAffected;

        Prescription prescription;
        if (saveType == SaveType.CREATE) {
            prescription = new Prescription();
            isEventsAffected = true;
        }
        else {
            prescription = prescriptionDAO.findOne(prescriptionDTO.getId());
            isEventsAffected = !prescription.getDuration().equals(prescriptionDTO.getDuration())
                    || !prescription.getTimePattern().getId().equals(timePattern.getId());
        }

        prescription = converter.toEntity(prescription, prescriptionDTO, patient, treatment, timePattern);

        // set current user as doctor
        prescription.setDoctor(userGetter.getCurrentUser());

        // only medicine could have a dosage
        if (prescription.getTreatment().getType() != TreatmentType.Medicine) {
            prescription.setDosage("");
        }

        // save prescription
        if (saveType == SaveType.CREATE) {
            prescriptionDAO.create(prescription);
        }
        else {
            prescriptionDAO.update(prescription);
        }

        // update events
        if (isEventsAffected) {
            eventService.deleteAllPlanned(prescription);
            eventService.createEvents(prescription);
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

    @Transactional(readOnly = true)
    public PrescriptionsTableDTO getTable(Map<String, String> data) {

        List<Prescription> prescriptions = prescriptionDAO.getAll(SortBy.CREATION);

        List<PrescriptionDTO> list = prescriptions.stream()
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

    /**
     * <p>Deletes prescription without completed or canceled events.
     * Set DELETED status to prescription with completed or canceled events.
     * Deletes app planned events.</p>
     * @param id prescription id
     */
    @Transactional
    public void delete(Long id) {
        Prescription prescription = prescriptionDAO.findOne(id);
        eventService.deleteAllPlanned(prescription);
        if (eventService.getAll(prescription).isEmpty()) {
            prescriptionDAO.delete(prescription);
        }
        else {
            prescription.setStatus(PrescriptionStatus.DELETED);
            prescriptionDAO.update(prescription);
        }
    }

}
