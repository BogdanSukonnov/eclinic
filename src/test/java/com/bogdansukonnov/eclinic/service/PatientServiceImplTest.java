package com.bogdansukonnov.eclinic.service;

import com.bogdansukonnov.eclinic.dao.PatientDao;
import com.bogdansukonnov.eclinic.dto.ResponsePatientDto;
import com.bogdansukonnov.eclinic.entity.Patient;
import com.bogdansukonnov.eclinic.entity.PatientStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceImplTest {

    @Mock
    private PatientDao patientDao;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private PrescriptionService prescriptionService;

    private PatientServiceImpl patientService;

    @BeforeEach
    void setUp() {
        patientService = new PatientServiceImpl(patientDao, modelMapper, prescriptionService);
    }

    @Test
    void addNewTest() {
        Patient patient = mock(Patient.class);
        ResponsePatientDto responsePatientDto = new ResponsePatientDto();
        when(modelMapper.map(responsePatientDto, Patient.class)).thenReturn(patient);
        when(patientDao.create(patient)).thenReturn(patient);

        patientService.addNew(responsePatientDto);

        verify(patient).setPatientStatus(PatientStatus.PATIENT);
        verify(patientDao).create(patient);
    }

    @Test
    void getPatientTable() {
    }

    @Test
    void dischargePatient() {
    }

    @Test
    void patientNameIsBusy() {
    }
}