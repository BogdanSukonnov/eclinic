package com.bogdansukonnov.eclinic.service;

import com.bogdansukonnov.eclinic.converter.PrescriptionConverter;
import com.bogdansukonnov.eclinic.dao.PatientDao;
import com.bogdansukonnov.eclinic.dao.PrescriptionDao;
import com.bogdansukonnov.eclinic.dao.TimePatternDao;
import com.bogdansukonnov.eclinic.dao.TreatmentDao;
import com.bogdansukonnov.eclinic.dto.RequestPrescriptionDto;
import com.bogdansukonnov.eclinic.dto.RequestTableDto;
import com.bogdansukonnov.eclinic.dto.ResponsePrescriptionDto;
import com.bogdansukonnov.eclinic.dto.TableDataDto;
import com.bogdansukonnov.eclinic.entity.*;
import com.bogdansukonnov.eclinic.exceptions.PrescriptionCreateException;
import com.bogdansukonnov.eclinic.exceptions.PrescriptionUpdateException;
import com.bogdansukonnov.eclinic.exceptions.VersionConflictException;
import com.bogdansukonnov.eclinic.security.SecurityContextAdapter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PrescriptionServiceImpl implements PrescriptionService {

    private PrescriptionDao prescriptionDao;
    private PrescriptionConverter converter;
    private TreatmentDao treatmentDao;
    private TimePatternDao timePatternDao;
    private PatientDao patientDao;
    private SecurityContextAdapter securityContextAdapter;
    private EventService eventService;

    @Override
    @Transactional(readOnly = true)
    public List<ResponsePrescriptionDto> getAll(OrderType orderType) {
        return prescriptionDao.getAll(orderType).stream()
                .map(prescription -> converter.toDto(prescription))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Long save(RequestPrescriptionDto dto,
                     LocalDateTime startDate, LocalDateTime inEndDate)
            throws PrescriptionCreateException, VersionConflictException {

        // set time to the end of the day
        LocalDateTime endDate = LocalDateTime.of(inEndDate.toLocalDate(), LocalTime.MAX);

        boolean isNew = dto.getId() == null;

        Treatment treatment = treatmentDao.findOne(dto.getTreatmentId());
        TimePattern timePattern = timePatternDao.findOne(dto.getTimePatternId());
        Patient patient = patientDao.findOne(dto.getPatientId());

        if (patient.getPatientStatus() != PatientStatus.PATIENT) {
            throw new PrescriptionCreateException(
                    "Can't create prescription to patient in status " + patient.getPatientStatus());
        }

        boolean isEventsAffected;

        Prescription prescription;
        if (isNew) {
            prescription = new Prescription();
            prescription.setStatus(PrescriptionStatus.PRESCRIBED);
            isEventsAffected = true;
        } else {
            prescription = prescriptionDao.findOne(dto.getId());
            if (!dto.getVersion().equals(prescription.getVersion())) {
                throw new VersionConflictException("Can't update prescription. Version conflict.");
            }
            isEventsAffected = !prescription.getStartDate().equals(startDate)
                    || !prescription.getEndDate().equals(endDate)
                    || !prescription.getTimePattern().equals(timePattern);
        }

        prescription = converter.toEntity(prescription, dto, patient, treatment, timePattern, startDate, endDate);

        // set current user as doctor
        prescription.setDoctor(securityContextAdapter.getCurrentUser());

        // only medicine could have a dosage
        if (prescription.getTreatment().getType() != TreatmentType.MEDICINE) {
            prescription.setDosage(0f);
            prescription.setDosageInfo("");
        }

        // save prescription
        if (isNew) {
            prescription = prescriptionDao.create(prescription);
        } else {
            prescription = prescriptionDao.update(prescription);
        }

        // update events
        if (isEventsAffected) {
            updateEvents(prescription);
        }

        return prescription.getId();
    }

    private void updateEvents(Prescription prescription) {
        eventService.deleteAllScheduled(prescription);
        eventService.createEvents(prescription);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponsePrescriptionDto getOne(Long id) {
        Prescription prescription = prescriptionDao.findOne(id);
        return converter.toDto(prescription);
    }

    @Override
    @Transactional(readOnly = true)
    public TableDataDto getPrescriptionTable(RequestTableDto data) {

        List<Prescription> prescriptions = prescriptionDao.getAll("startDate desc", data.getSearch(),
                data.getOffset(), data.getLimit(), data.getParentId());

        Long totalFiltered = prescriptionDao.getTotalFiltered(data.getSearch(), data.getParentId());

        List<ResponsePrescriptionDto> list = prescriptions.stream()
                .map(prescription -> converter.toDto(prescription))
                .collect(Collectors.toList());

        return new TableDataDto<>(list, data.getDraw(), totalFiltered, totalFiltered);

    }

    /**
     * <p>Cancels prescription.
     * Set CANCELED status to prescription.
     * Set CANCELED status to all scheduled prescription's events.</p>
     *
     * @param id prescription id
     */
    @Override
    @Transactional
    public void cancelPrescription(Long id, Integer version)
            throws PrescriptionUpdateException, VersionConflictException {
        Prescription prescription = prescriptionDao.findOne(id);
        if (!prescription.getStatus().equals(PrescriptionStatus.PRESCRIBED)) {
            throw new PrescriptionUpdateException("Only active prescriptions can be canceled.");
        }
        if (!version.equals(prescription.getVersion())) {
            throw new VersionConflictException("Can't cancel prescription. Version conflict.");
        }
        eventService.cancelAllScheduled(prescription, "Prescription cancelled");
        prescription.setStatus(PrescriptionStatus.CANCELED);
        prescriptionDao.update(prescription);
    }

    /**
     * complete patient's active prescriptions
     *
     * @param patient patient
     */
    @Override
    @Transactional
    public void completeAllActive(Patient patient) {
        List<Prescription> prescriptions = prescriptionDao.getAll(patient);
        for (Prescription prescription : prescriptions) {
            if (!prescription.getStatus().equals(PrescriptionStatus.CANCELED) &&
                    prescription.getEndDate().isAfter(LocalDateTime.now())) {
                prescription.setEndDate(LocalDateTime.now());
                prescription = prescriptionDao.update(prescription);
                updateEvents(prescription);
            }
        }
    }

}
