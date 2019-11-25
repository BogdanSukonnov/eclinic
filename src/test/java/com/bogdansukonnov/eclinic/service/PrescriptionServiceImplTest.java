package com.bogdansukonnov.eclinic.service;

import com.bogdansukonnov.eclinic.converter.PrescriptionConverter;
import com.bogdansukonnov.eclinic.dao.PatientDao;
import com.bogdansukonnov.eclinic.dao.PrescriptionDao;
import com.bogdansukonnov.eclinic.dao.TimePatternDao;
import com.bogdansukonnov.eclinic.dao.TreatmentDao;
import com.bogdansukonnov.eclinic.dto.RequestTableDto;
import com.bogdansukonnov.eclinic.security.SecurityContextAdapter;
import org.mockito.Mock;

public abstract class PrescriptionServiceImplTest {
    protected PrescriptionServiceImpl prescriptionService;
    @Mock
    protected PrescriptionDao prescriptionDao;
    @Mock
    protected PrescriptionConverter converter;
    @Mock
    protected EventService eventService;
    @Mock
    protected RequestTableDto data;
    @Mock
    protected TreatmentDao treatmentDao;
    @Mock
    protected TimePatternDao timePatternDao;
    @Mock
    protected PatientDao patientDao;
    @Mock
    protected SecurityContextAdapter securityContextAdapter;

    protected void prescriptionServiceInit() {

        prescriptionService = new PrescriptionServiceImpl(prescriptionDao, converter, treatmentDao, timePatternDao
                , patientDao, securityContextAdapter, eventService);

    }
}
