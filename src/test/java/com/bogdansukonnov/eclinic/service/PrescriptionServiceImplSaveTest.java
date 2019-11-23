package com.bogdansukonnov.eclinic.service;

import com.bogdansukonnov.eclinic.converter.PrescriptionConverter;
import com.bogdansukonnov.eclinic.dao.PatientDao;
import com.bogdansukonnov.eclinic.dao.PrescriptionDao;
import com.bogdansukonnov.eclinic.dao.TimePatternDao;
import com.bogdansukonnov.eclinic.dao.TreatmentDao;
import com.bogdansukonnov.eclinic.dto.RequestPrescriptionDto;
import com.bogdansukonnov.eclinic.entity.*;
import com.bogdansukonnov.eclinic.exceptions.PrescriptionCreateException;
import com.bogdansukonnov.eclinic.exceptions.PrescriptionUpdateException;
import com.bogdansukonnov.eclinic.security.SecurityContextAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PrescriptionServiceImplSaveTest {

    @Mock
    RequestPrescriptionDto dto;
    @Mock
    private PrescriptionDao prescriptionDao;
    @Mock
    private PrescriptionConverter converter;
    @Mock
    private TreatmentDao treatmentDao;
    @Mock
    private TimePatternDao timePatternDao;
    @Mock
    private PatientDao patientDao;
    @Mock
    private SecurityContextAdapter securityContextAdapter;
    @Mock
    private EventService eventService;
    @Mock
    private Prescription prescription;
    private PrescriptionServiceImpl prescriptionService;
    private LocalDateTime startDate;
    private LocalDateTime inEndDate;
    private LocalDateTime endDate;
    private Patient patient;
    private Treatment treatment;
    private TimePattern timePattern;
    private AppUser appUser;
    private long id;

    @BeforeEach
    void setUp() {

        prescriptionService = new PrescriptionServiceImpl(prescriptionDao, converter, treatmentDao, timePatternDao
                , patientDao, securityContextAdapter, eventService);

        startDate = LocalDateTime.parse("2020-01-01T10:00");
        inEndDate = LocalDateTime.parse("2020-01-07T10:00");
        endDate = LocalDateTime.of(inEndDate.toLocalDate(), LocalTime.MAX);

        patient = new Patient();
        patient.setPatientStatus(PatientStatus.PATIENT);

        when(patientDao.findOne(anyLong())).thenReturn(patient);
        id = 7L;
        when(dto.getId()).thenReturn(id);

        treatment = new Treatment();
        when(treatmentDao.findOne(anyLong())).thenReturn(treatment);

        timePattern = new TimePattern();
        when(timePatternDao.findOne(anyLong())).thenReturn(timePattern);

    }

    private void setupSuccess() {
        appUser = new AppUser();
        when(securityContextAdapter.getCurrentUser()).thenReturn(appUser);
        when(converter.toEntity(any(), any(), any(), any(), any(), any(), any())).thenReturn(prescription);
        when(prescription.getTreatment()).thenReturn(treatment);
        when(prescription.getId()).thenReturn(id);
    }

    private void setupSuccessUpdate() {
        setupSuccess();
        when(prescriptionDao.findOne(id)).thenReturn(prescription);
        when(prescriptionDao.update(any())).thenReturn(prescription);
        when(prescription.getStartDate()).thenReturn(startDate);
        lenient().when(prescription.getEndDate()).thenReturn(endDate);
        lenient().when(prescription.getTimePattern()).thenReturn(timePattern);
    }

    @Test
    void saveExistingTest() throws PrescriptionCreateException, PrescriptionUpdateException {

        setupSuccessUpdate();

        assertEquals(id, prescriptionService.save(dto, startDate, endDate));

        verify(treatmentDao).findOne(anyLong());
        verify(timePatternDao).findOne(anyLong());
        verify(patientDao).findOne(anyLong());
        verify(converter).toEntity(prescription, dto, patient, treatment, timePattern, startDate, endDate);
        verify(prescription).setDoctor(appUser);
        verify(prescriptionDao).update(prescription);
        // no update events
        verify(eventService, times(0)).deleteAllScheduled(any());
        verify(eventService, times(0)).createEvents(any());

    }

    @Test
    void saveExistingChangedTimePatternTest() throws PrescriptionCreateException, PrescriptionUpdateException {

        setupSuccessUpdate();
        when(prescription.getTimePattern()).thenReturn(new TimePattern());

        prescriptionService.save(dto, startDate, endDate);

        verify(eventService).deleteAllScheduled(any());
        verify(eventService).createEvents(any());

    }

    @Test
    void saveExistingChangedStartDateTest() throws PrescriptionCreateException, PrescriptionUpdateException {

        setupSuccessUpdate();

        prescriptionService.save(dto, endDate, endDate);

        verify(eventService).deleteAllScheduled(any());
        verify(eventService).createEvents(any());

    }

    @Test
    void saveExistingChangedEndDateTest() throws PrescriptionCreateException, PrescriptionUpdateException {

        setupSuccessUpdate();

        prescriptionService.save(dto, startDate, startDate);

        verify(eventService).deleteAllScheduled(any());
        verify(eventService).createEvents(any());

    }

    @Test
    void saveNewTest() throws PrescriptionCreateException, PrescriptionUpdateException {

        setupSuccess();

        when(dto.getId()).thenReturn(null);
        when(prescriptionDao.create(any())).thenReturn(prescription);

        prescriptionService.save(dto, startDate, inEndDate);
        verify(prescriptionDao).create(prescription);
        // test that end date set to end of the day
        verify(converter).toEntity(any(), any(), any(), any(), any(), any(), eq(endDate));

        verify(eventService).deleteAllScheduled(prescription);
        verify(eventService).createEvents(prescription);

    }

    @Test
    void saveNewClearProcedureDosageTest() throws PrescriptionCreateException, PrescriptionUpdateException {

        setupSuccess();

        treatment.setType(TreatmentType.PROCEDURE);
        when(dto.getId()).thenReturn(null);
        when(prescriptionDao.create(any())).thenReturn(prescription);

        prescriptionService.save(dto, startDate, inEndDate);

        verify(prescription).setDosage("");
    }

    @Test
    void saveFailToWrongPatientStatusTest() throws PrescriptionCreateException, PrescriptionUpdateException {

        Patient patient = new Patient();
        patient.setPatientStatus(PatientStatus.DISCHARGED);

        when(patientDao.findOne(anyLong())).thenReturn(patient);

        assertThrows(PrescriptionCreateException.class, () -> {
            prescriptionService.save(dto, startDate, endDate);
        });

    }

}