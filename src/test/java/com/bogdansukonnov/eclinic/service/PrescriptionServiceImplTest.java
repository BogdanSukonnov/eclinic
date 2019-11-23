package com.bogdansukonnov.eclinic.service;

import com.bogdansukonnov.eclinic.converter.PrescriptionConverter;
import com.bogdansukonnov.eclinic.dao.PatientDao;
import com.bogdansukonnov.eclinic.dao.PrescriptionDao;
import com.bogdansukonnov.eclinic.dao.TimePatternDao;
import com.bogdansukonnov.eclinic.dao.TreatmentDao;
import com.bogdansukonnov.eclinic.dto.RequestTableDto;
import com.bogdansukonnov.eclinic.dto.TableDataDto;
import com.bogdansukonnov.eclinic.entity.Patient;
import com.bogdansukonnov.eclinic.entity.Prescription;
import com.bogdansukonnov.eclinic.entity.PrescriptionStatus;
import com.bogdansukonnov.eclinic.exceptions.PrescriptionUpdateException;
import com.bogdansukonnov.eclinic.security.SecurityContextAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PrescriptionServiceImplTest {

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
    private RequestTableDto data;

    private PrescriptionServiceImpl prescriptionService;

    @BeforeEach
    void setUp() {

        prescriptionService = new PrescriptionServiceImpl(prescriptionDao, converter, treatmentDao, timePatternDao
                , patientDao, securityContextAdapter, eventService);

    }

    @Test
    void getPrescriptionTableTest() {

        Prescription prescription = new Prescription();

        when(prescriptionDao.getAll(anyString(), any(), anyInt(), anyInt(), anyLong()))
                .thenReturn(Collections.singletonList(prescription));

        Integer draw = 3;
        when(data.getDraw()).thenReturn(draw);

        TableDataDto tableDataDto = prescriptionService.getPrescriptionTable(data);

        verify(converter).toDto(any());
        verify(prescriptionDao).getAll(eq("startDate desc"), any(), anyInt(), anyInt(), anyLong());
        verify(prescriptionDao).getTotalFiltered(any(), anyLong());
        assertEquals(draw, tableDataDto.getDraw());

    }

    @Test
    void cancelPrescriptionTest() throws PrescriptionUpdateException {

        long id = 7L;
        Prescription prescription = new Prescription();
        when(prescriptionDao.findOne(id)).thenReturn(prescription);

        prescription.setStatus(PrescriptionStatus.CANCELED);
        // should fail on cancelled prescription
        assertThrows(PrescriptionUpdateException.class, () -> prescriptionService.cancelPrescription(id));

        prescription.setStatus(PrescriptionStatus.PRESCRIBED);
        prescriptionService.cancelPrescription(id);

        verify(eventService).cancelAllScheduled(prescription, "Prescription cancelled");
        assertEquals(PrescriptionStatus.CANCELED, prescription.getStatus());
        verify(prescriptionDao).update(prescription);
    }

    @Test
    void completeAllActiveTest() {
        LocalDateTime tomorrow = LocalDateTime.now().plusDays(1);

        Prescription canceledPrescription = mock(Prescription.class);
        when(canceledPrescription.getStatus()).thenReturn(PrescriptionStatus.CANCELED);

        Prescription activePrescription = mock(Prescription.class);
        when(activePrescription.getStatus()).thenReturn(PrescriptionStatus.PRESCRIBED);
        when(activePrescription.getEndDate()).thenReturn(tomorrow);
        when(prescriptionDao.getAll(any(Patient.class))).thenReturn(Arrays.asList(canceledPrescription, activePrescription));
        when(prescriptionDao.update(activePrescription)).thenReturn(activePrescription);

        prescriptionService.completeAllActive(new Patient());

        verify(canceledPrescription, times(0)).setEndDate(any());
        verify(prescriptionDao, times(0)).update(canceledPrescription);
        verify(eventService, times(0)).deleteAllScheduled(canceledPrescription);
        verify(eventService, times(0)).createEvents(canceledPrescription);

        verify(activePrescription).setEndDate(any());
        verify(prescriptionDao).update(activePrescription);
        verify(eventService).deleteAllScheduled(activePrescription);
        verify(eventService).createEvents(activePrescription);

    }

}