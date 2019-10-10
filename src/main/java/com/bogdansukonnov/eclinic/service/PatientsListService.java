package com.bogdansukonnov.eclinic.service;

import com.bogdansukonnov.eclinic.dao.PatientDAO;
import com.bogdansukonnov.eclinic.dto.PatientDTO;
import com.bogdansukonnov.eclinic.entity.Patient;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientsListService {

    @NonNull
    private PatientDAO patientDAO;

    @NonNull
    private ModelMapper modelMapper;

    @Transactional( readOnly = true )
    public List<PatientDTO> getAll() {
        return patientDAO.getAll().stream()
                .map(patient -> modelMapper.map(patient, PatientDTO.class))
                .collect(Collectors.toList());
    }

}
