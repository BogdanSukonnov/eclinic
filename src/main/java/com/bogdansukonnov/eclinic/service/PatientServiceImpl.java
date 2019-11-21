package com.bogdansukonnov.eclinic.service;

import com.bogdansukonnov.eclinic.config.EClinicConstants;
import com.bogdansukonnov.eclinic.dao.PatientDao;
import com.bogdansukonnov.eclinic.dto.RequestTableDto;
import com.bogdansukonnov.eclinic.dto.ResponsePatientDto;
import com.bogdansukonnov.eclinic.dto.TableDataDto;
import com.bogdansukonnov.eclinic.entity.Patient;
import com.bogdansukonnov.eclinic.entity.PatientStatus;
import com.bogdansukonnov.eclinic.exceptions.PatientUpdateException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PatientServiceImpl implements PatientService {

    private PatientDao patientDao;
    private ModelMapper modelMapper;
    private PrescriptionService prescriptionService;

    @Override
    @Transactional(readOnly = true)
    public List<ResponsePatientDto> getAll(OrderType orderType) {
        return patientDao.getAll(orderType).stream()
                .map(patient -> modelMapper.map(patient, ResponsePatientDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ResponsePatientDto getOne(Long id) {
        Patient patient = patientDao.findOne(id);
        return modelMapper.map(patient, ResponsePatientDto.class);
    }

    @Override
    @Transactional
    public Long addNew(ResponsePatientDto responsePatientDto) {
        Patient patient = modelMapper.map(responsePatientDto, Patient.class);
        patient.setPatientStatus(PatientStatus.PATIENT);
        patient = patientDao.create(patient);
        return patient.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public TableDataDto getPatientTable(RequestTableDto data) {

        List<Patient> patients = patientDao.getAll(data.getOrderField(), data.getSearch(),
                data.getOffset(), data.getLimit(), null);

        Long totalFiltered = patientDao.getTotalFiltered(data.getSearch(), null);

        List<ResponsePatientDto> list = patients.stream()
                .map(patient -> {
                    ResponsePatientDto dto = modelMapper.map(patient, ResponsePatientDto.class);
                    dto.setFormattedDate(patient.getCreatedDateTime().format(EClinicConstants.dateFormatter));
                    return dto;
                })
                .collect(Collectors.toList());

        return new TableDataDto<>(list, data.getDraw(), totalFiltered, totalFiltered);
    }

    @Override
    @Transactional
    public void dischargePatient(Long id, Integer version) throws PatientUpdateException {
        Patient patient = patientDao.findOne(id);
        if (patient.getPatientStatus() != PatientStatus.PATIENT) {
            throw new PatientUpdateException("Can't discharge patient in status " + patient.getPatientStatus());
        }
        if (!patient.getVersion().equals(version)) {
            throw new PatientUpdateException("Can't discharge patient. Version conflict.");
        }
        patient.setPatientStatus(PatientStatus.DISCHARGED);
        patient = patientDao.update(patient);

        //complete active prescriptions
        prescriptionService.completeAllActive(patient);
    }

    @Override
    @Transactional
    public boolean patientNameIsBusy(String fullName) {
        Optional<Patient> patient = patientDao.findOne(fullName.trim());
        return patient.isPresent();
    }
}
