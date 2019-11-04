package com.bogdansukonnov.eclinic.service;

import com.bogdansukonnov.eclinic.converter.PrescriptionConverter;
import com.bogdansukonnov.eclinic.dao.PatientDAO;
import com.bogdansukonnov.eclinic.dao.PrescriptionDAO;
import com.bogdansukonnov.eclinic.dao.TimePatternDAO;
import com.bogdansukonnov.eclinic.dao.TreatmentDAO;
import com.bogdansukonnov.eclinic.dto.RequestPrescriptionDTO;
import com.bogdansukonnov.eclinic.dto.RequestTableDTO;
import com.bogdansukonnov.eclinic.dto.ResponsePrescriptionDTO;
import com.bogdansukonnov.eclinic.dto.TableDataDTO;
import com.bogdansukonnov.eclinic.entity.*;
import com.bogdansukonnov.eclinic.exceptions.PrescriptionCreateException;
import com.bogdansukonnov.eclinic.exceptions.PrescriptionUpdateException;
import com.bogdansukonnov.eclinic.security.UserGetter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    public List<ResponsePrescriptionDTO> getAll(OrderType orderType) {
        return prescriptionDAO.getAll(orderType).stream()
                .map(prescription -> converter.toDTO(prescription))
                .collect(Collectors.toList());
    }

    @Transactional
    public Long save(RequestPrescriptionDTO dto,
                     LocalDateTime startDate, LocalDateTime endDate)
            throws PrescriptionCreateException {

        boolean isNew = dto.getId() == null;

        Treatment treatment = treatmentDAO.findOne(dto.getTreatmentId());
        TimePattern timePattern = timePatternDAO.findOne(dto.getTimePatternId());
        Patient patient = patientDAO.findOne(dto.getPatientId());

        boolean isEventsAffected;

        Prescription prescription;
        if (isNew) {
            if (patient.getPatientStatus() != PatientStatus.PATIENT) {
                throw new PrescriptionCreateException(
                        "Can't create prescription to patient in status " + patient.getPatientStatus());
            }
            prescription = new Prescription();
            prescription.setStatus(PrescriptionStatus.PRESCRIBED);
            isEventsAffected = true;
        }
        else {
            prescription = prescriptionDAO.findOne(dto.getId());
            isEventsAffected = !prescription.getStartDate().equals(startDate)
                    || !prescription.getEndDate().equals(endDate)
                    || !prescription.getTimePattern().equals(timePattern);
        }

        prescription = converter.toEntity(prescription, dto, patient, treatment, timePattern, startDate, endDate);

        // set current user as doctor
        prescription.setDoctor(userGetter.getCurrentUser());

        // only medicine could have a dosage
        if (prescription.getTreatment().getType() != TreatmentType.MEDICINE) {
            prescription.setDosage("");
        }

        // save prescription
        if (isNew) {
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

        return prescription.getId();
    }

    @Transactional(readOnly = true)
    public ResponsePrescriptionDTO getOne(Long id) {
        Prescription prescription = prescriptionDAO.findOne(id);
        return converter.toDTO(prescription);
    }

    @Transactional(readOnly = true)
    public TableDataDTO getTable(RequestTableDTO data) {

        List<Prescription> prescriptions = prescriptionDAO.getAll("startDate desc", data.getSearch(),
                data.getOffset(), data.getLimit(), data.getParentId());

        Long totalFiltered = prescriptionDAO.getTotalFiltered(data.getSearch(), data.getParentId());

        List<ResponsePrescriptionDTO> list = prescriptions.stream()
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
        if (!prescription.getStatus().equals(PrescriptionStatus.PRESCRIBED)) {
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
