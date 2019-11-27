package com.bogdansukonnov.eclinic.service;

import com.bogdansukonnov.eclinic.dto.TableDataDto;
import com.bogdansukonnov.eclinic.entity.Patient;
import com.bogdansukonnov.eclinic.entity.Prescription;
import com.bogdansukonnov.eclinic.entity.PrescriptionStatus;
import com.bogdansukonnov.eclinic.exceptions.PrescriptionUpdateException;
import com.bogdansukonnov.eclinic.exceptions.VersionConflictException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class PrescriptionServiceImplOtherTest extends PrescriptionServiceImplTest {

    @BeforeEach
    void setUp() {
        super.prescriptionServiceInit();
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
    void cancelPrescriptionTest() throws PrescriptionUpdateException, VersionConflictException {

        long id = 7L;
        int version = 0;
        Prescription prescription = new Prescription();
        prescription.setVersion(version);
        when(prescriptionDao.findOne(id)).thenReturn(prescription);

        prescription.setStatus(PrescriptionStatus.CANCELED);
        // should fail on cancelled prescription
        assertThrows(PrescriptionUpdateException.class, () -> prescriptionService.cancelPrescription(id, version));

        prescription.setStatus(PrescriptionStatus.PRESCRIBED);
        prescriptionService.cancelPrescription(id, version);

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