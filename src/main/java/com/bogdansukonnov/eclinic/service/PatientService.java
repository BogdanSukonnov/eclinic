package com.bogdansukonnov.eclinic.service;

import com.bogdansukonnov.eclinic.dao.PatientDAO;
import com.bogdansukonnov.eclinic.dto.RequestTableDTO;
import com.bogdansukonnov.eclinic.dto.ResponsePatientDTO;
import com.bogdansukonnov.eclinic.dto.TableDataDTO;
import com.bogdansukonnov.eclinic.entity.Patient;
import com.bogdansukonnov.eclinic.entity.PatientStatus;
import com.bogdansukonnov.eclinic.exceptions.PatientUpdateException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PatientService {

    private PatientDAO patientDAO;
    private ModelMapper modelMapper;
    private PrescriptionService prescriptionService;

    @Transactional(readOnly = true)
    public List<ResponsePatientDTO> getAll(OrderType orderType) {
        return patientDAO.getAll(orderType).stream()
                .map(patient -> modelMapper.map(patient, ResponsePatientDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ResponsePatientDTO getOne(Long id) {
        Patient patient = patientDAO.findOne(id);
        return modelMapper.map(patient, ResponsePatientDTO.class);
    }

    @Transactional
    public Long addNew(ResponsePatientDTO responsePatientDTO) {
        Patient patient = modelMapper.map(responsePatientDTO, Patient.class);
        patient.setPatientStatus(PatientStatus.PATIENT);
        patient = patientDAO.create(patient);
        return patient.getId();
    }

    @Transactional(readOnly = true)
    public TableDataDTO getTable(RequestTableDTO data) {

        List<Patient> patients = patientDAO.getAll(data.getOrderField(), data.getSearch(),
                data.getOffset(), data.getLimit(), null);

        Long totalFiltered = patientDAO.getTotalFiltered(data.getSearch(), null);

        List<ResponsePatientDTO> list = patients.stream()
                .map(patient -> {
                    ResponsePatientDTO dto = modelMapper.map(patient, ResponsePatientDTO.class);
                    dto.setFormattedDate(patient.getCreatedDateTime().format(DateTimeFormatter.ofPattern("dd.MM.yy")));
                    return dto;
                })
                .collect(Collectors.toList());

        return new TableDataDTO<>(list, data.getDraw(), totalFiltered, totalFiltered);
    }

    @Transactional
    public void dischargePatient(Long id, Integer version) throws PatientUpdateException {
        Patient patient = patientDAO.findOne(id);
        if (patient.getPatientStatus() != PatientStatus.PATIENT) {
            throw new PatientUpdateException("Can't discharge patient in status " + patient.getPatientStatus());
        }
        if (!patient.getVersion().equals(version)) {
            throw new PatientUpdateException("Can't discharge patient. Version conflict.");
        }
        patient.setPatientStatus(PatientStatus.DISCHARGED);
        patient = patientDAO.update(patient);

        //complete active prescriptions
        prescriptionService.completeAllActive(patient);
    }

    @Transactional
    public boolean patientNameIsBusy(String fullName) {
        Optional<Patient> patient = patientDAO.findOne(fullName.trim());
        return patient.isPresent();
    }
}
