package com.bogdansukonnov.eclinic.service;

import com.bogdansukonnov.eclinic.dao.PatientDAO;
import com.bogdansukonnov.eclinic.dto.PatientDTO;
import com.bogdansukonnov.eclinic.dto.RequestTableDTO;
import com.bogdansukonnov.eclinic.dto.TableDataDTO;
import com.bogdansukonnov.eclinic.entity.Patient;
import com.bogdansukonnov.eclinic.entity.PatientStatus;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PatientService {

    private PatientDAO patientDAO;
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<PatientDTO> getAll(PrescriptionService.OrderType orderType) {
        return patientDAO.getAll(orderType).stream()
                .map(patient -> modelMapper.map(patient, PatientDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PatientDTO getOne(Long id) {
        Patient patient = patientDAO.findOne(id);
        return modelMapper.map(patient, PatientDTO.class);
    }

    @Transactional
    public Long addNew(PatientDTO patientDTO) {
        Patient patient = modelMapper.map(patientDTO, Patient.class);
        patient.setPatientStatus(PatientStatus.PATIENT);
        patient = patientDAO.create(patient);
        return patient.getId();
    }

    @Transactional(readOnly = true)
    public TableDataDTO getTable(RequestTableDTO data) {

        List<Patient> patients = patientDAO.getAll(data.getOrderField(), data.getSearch(),
                data.getOffset(), data.getLimit(), null);

        Long totalFiltered = patientDAO.getTotalFiltered(data.getSearch(), null);

        List<PatientDTO> list = patients.stream()
                .map(patient -> {
                    PatientDTO dto = modelMapper.map(patient, PatientDTO.class);
                    dto.setFormattedDate(patient.getCreatedDateTime().format(DateTimeFormatter.ofPattern("dd.MM.yy")));
                    return dto;
                })
                .collect(Collectors.toList());

        return new TableDataDTO<>(list, data.getDraw(), totalFiltered, totalFiltered);
    }

}
