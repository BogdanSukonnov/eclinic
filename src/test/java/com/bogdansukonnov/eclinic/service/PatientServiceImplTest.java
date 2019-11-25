package com.bogdansukonnov.eclinic.service;

import com.bogdansukonnov.eclinic.dao.PatientDao;
import com.bogdansukonnov.eclinic.dto.ResponsePatientDto;
import com.bogdansukonnov.eclinic.entity.Patient;
import com.bogdansukonnov.eclinic.entity.PatientStatus;
import com.bogdansukonnov.eclinic.exceptions.PatientUpdateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class PatientServiceImplTest {

    @Mock
    private PatientDao patientDao;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private PrescriptionService prescriptionService;
    @Mock
    Patient patient;

    private PatientServiceImpl patientService;
    private long id = 7L;

    @BeforeEach
    void setUp() {
        patientService = new PatientServiceImpl(patientDao, modelMapper, prescriptionService);
    }

    @Test
    void addNewTest() {
        ResponsePatientDto responsePatientDto = new ResponsePatientDto();
        when(modelMapper.map(responsePatientDto, Patient.class)).thenReturn(patient);
        when(patientDao.create(patient)).thenReturn(patient);

        patientService.addNew(responsePatientDto);

        verify(patient).setPatientStatus(PatientStatus.PATIENT);
        verify(patientDao).create(patient);
    }

    @Test
    void dischargePatientWrongStatusFailTest() {
        when(patientDao.findOne(id)).thenReturn(patient);
        when(patient.getPatientStatus()).thenReturn(PatientStatus.DISCHARGED);
        assertThrows(PatientUpdateException.class, () -> patientService.dischargePatient(id, 0));
    }

    @Test
    void dischargePatientWrongVersionFailTest() {
        int incomingVersion = 7;
        int savedVersion = 3;
        when(patientDao.findOne(id)).thenReturn(patient);
        when(patient.getPatientStatus()).thenReturn(PatientStatus.PATIENT);
        when(patient.getVersion()).thenReturn(savedVersion);
        assertThrows(PatientUpdateException.class, () -> patientService.dischargePatient(id, incomingVersion));
    }

    @Test
    void dischargePatientTest() throws PatientUpdateException {
        int version = 21;
        when(patientDao.findOne(id)).thenReturn(patient);
        when(patientDao.update(patient)).thenReturn(patient);
        when(patient.getPatientStatus()).thenReturn(PatientStatus.PATIENT);
        when(patient.getVersion()).thenReturn(version);

        patientService.dischargePatient(id, version);

        verify(patient).setPatientStatus(PatientStatus.DISCHARGED);
        verify(patientDao).update(patient);
        verify(prescriptionService).completeAllActive(patient);
    }

}