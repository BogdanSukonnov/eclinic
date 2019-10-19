package com.bogdansukonnov.eclinic.converter;

import com.bogdansukonnov.eclinic.dto.PatientDTO;
import com.bogdansukonnov.eclinic.entity.Patient;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PatientConverter {

    private ModelMapper modelMapper;

    public PatientDTO toDTO(Patient patient) {
        return modelMapper.map(patient, PatientDTO.class);
    }

    public Patient toEntity(PatientDTO dto) {
        return modelMapper.map(dto, Patient.class);
    }

}
