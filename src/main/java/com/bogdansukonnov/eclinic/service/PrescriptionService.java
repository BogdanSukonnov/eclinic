package com.bogdansukonnov.eclinic.service;

import com.bogdansukonnov.eclinic.converter.PrescriptionConverter;
import com.bogdansukonnov.eclinic.dao.PatientDAO;
import com.bogdansukonnov.eclinic.dao.PrescriptionDAO;
import com.bogdansukonnov.eclinic.dao.TimePatternDAO;
import com.bogdansukonnov.eclinic.dao.TreatmentDAO;
import com.bogdansukonnov.eclinic.dto.PrescriptionDTO;
import com.bogdansukonnov.eclinic.dto.RequestTableDTO;
import com.bogdansukonnov.eclinic.dto.TableDataDTO;
import com.bogdansukonnov.eclinic.entity.*;
import com.bogdansukonnov.eclinic.exceptions.PrescriptionUpdateException;
import com.bogdansukonnov.eclinic.security.UserGetter;
import lombok.AllArgsConstructor;
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
    private EventService eventService;

    @Transactional(readOnly = true)
    public List<PrescriptionDTO> getAll(OrderType orderType) {
        return prescriptionDAO.getAll(orderType).stream()
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
    public TableDataDTO getTable(RequestTableDTO data) {

        List<Prescription> prescriptions = prescriptionDAO.getAll("createdDateTime desc", data.getSearch(),
                data.getOffset(), data.getLimit(), data.getParentId());

        Long totalFiltered = prescriptionDAO.getTotalFiltered(data.getSearch(), data.getParentId());

        List<PrescriptionDTO> list = prescriptions.stream()
                .map(prescription -> converter.toDTO(prescription))
                .collect(Collectors.toList());

        return new TableDataDTO<>(list, data.getDraw(), totalFiltered, totalFiltered);

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

    public enum OrderType {
        CREATION, NAME
    }
}
