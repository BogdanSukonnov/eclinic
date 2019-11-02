package com.bogdansukonnov.eclinic.service;

import com.bogdansukonnov.eclinic.dao.PatientDAO;
import com.bogdansukonnov.eclinic.dao.SortBy;
import com.bogdansukonnov.eclinic.dto.PatientDTO;
import com.bogdansukonnov.eclinic.dto.TableDataDTO;
import com.bogdansukonnov.eclinic.entity.Patient;
import com.bogdansukonnov.eclinic.entity.PatientStatus;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PatientService {

    private PatientDAO patientDAO;
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<PatientDTO> getAll(SortBy sortBy) {
        return patientDAO.getAll(sortBy).stream()
                .map(patient -> modelMapper.map(patient, PatientDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PatientDTO getOne(Long id) {
        Patient patient = patientDAO.findOne(id);
        return modelMapper.map(patient, PatientDTO.class);
    }

    @Transactional
    public void addNew(PatientDTO patientDTO) {
        Patient patient = modelMapper.map(patientDTO, Patient.class);
        patient.setPatientStatus(PatientStatus.PATIENT);
        patientDAO.create(patient);
    }

    @Transactional(readOnly = true)
    public TableDataDTO getTable(Map<String, String> data) {

        String orderField = data.get("orderColumn") + " " + data.get("orderDirection");
        String search = data.get("search[value]");
        int offset = Integer.parseInt(data.get("start"));
        int limit = Integer.parseInt(data.get("length"));
        List<Patient> patients = patientDAO.getAll(orderField, search, offset, limit);

        Long count = patientDAO.getCount(search);

        List<PatientDTO> list = patients.stream()
                .map(patient -> {
                    PatientDTO dto = modelMapper.map(patient, PatientDTO.class);
                    dto.setFormattedDate(patient.getCreatedDateTime().format(DateTimeFormatter.ofPattern("dd.MM.yy")));
                    return dto;
                })
                .collect(Collectors.toList());

        return new TableDataDTO<>(list, Integer.parseInt(data.get("draw")), count, count);
    }

}
