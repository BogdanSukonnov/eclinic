package com.bogdansukonnov.eclinic.service;

import com.bogdansukonnov.eclinic.converter.PrescriptionConverter;
import com.bogdansukonnov.eclinic.dao.*;
import com.bogdansukonnov.eclinic.dto.PrescriptionDTO;
import com.bogdansukonnov.eclinic.dto.TableDataDTO;
import com.bogdansukonnov.eclinic.entity.*;
import com.bogdansukonnov.eclinic.exceptions.PrescriptionUpdateException;
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

    private PrescriptionDAOOld prescriptionDAO;
    private PrescriptionConverter converter;
    private TreatmentDAOOld treatmentDAO;
    private TimePatternDAOOld timePatternDAO;
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
            prescription.setStatus(PrescriptionStatus.ACTIVE);
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
            eventService.cancelAllScheduled(prescription);
            eventService.createEvents(prescription);
        }
    }

    @Transactional(readOnly = true)
    public PrescriptionDTO getOne(Long id) {
        Prescription prescription = prescriptionDAO.findOne(id);
        return converter.toDTO(prescription);
    }

    @Transactional(readOnly = true)
    public TableDataDTO getTableByPatient(Long patientId, Map<String, String> data) {

        List<Prescription> allByPatient = prescriptionDAO.getAllByPatient(patientId);

        List<PrescriptionDTO> list = allByPatient.stream()
                .map(prescription -> converter.toDTO(prescription))
                .collect(Collectors.toList());

        return new TableDataDTO<>(list
                , Integer.parseInt(data.get("draw")), (long) list.size(), (long) list.size());
    }

    @Transactional(readOnly = true)
    public TableDataDTO getTable(Map<String, String> data) {

        String search = data.get("search[value]");
        int offset = Integer.parseInt(data.get("start"));
        int limit = Integer.parseInt(data.get("length"));
        List<Prescription> prescriptions = prescriptionDAO.getAll(SortBy.CREATION);

        List<PrescriptionDTO> list = prescriptions.stream()
                .map(prescription -> converter.toDTO(prescription))
                .collect(Collectors.toList());

        return new TableDataDTO<>(list
                , Integer.parseInt(data.get("draw")), (long) list.size(), (long) list.size());
    }

    /**
     * <p>Cancels prescription.
     * Set CANCELED status to prescription.
     * Set CANCELED status to all scheduled prescription's events.</p>
     * @param id prescription id
     */
    @Transactional
    public void cancelPrescription(Long id) throws PrescriptionUpdateException {
        Prescription prescription = prescriptionDAO.findOne(id);
        if (!prescription.getStatus().equals(PrescriptionStatus.ACTIVE)) {
            throw new PrescriptionUpdateException("Only active prescriptions can be canceled.");
        }
        eventService.cancelAllScheduled(prescription);
        prescription.setStatus(PrescriptionStatus.CANCELED);
        prescriptionDAO.update(prescription);
    }

}
