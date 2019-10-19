package com.bogdansukonnov.eclinic.converter;

import com.bogdansukonnov.eclinic.dto.PatientDTO;
import com.bogdansukonnov.eclinic.dto.PrescriptionDTO;
import com.bogdansukonnov.eclinic.entity.Patient;
import com.bogdansukonnov.eclinic.entity.Prescription;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PrescriptionConverter {

    private ModelMapper modelMapper;

    public PrescriptionDTO toDTO(Prescription prescription) {
        PrescriptionDTO dto = modelMapper.map(prescription, PrescriptionDTO.class);
        return dto;
    }

    public Prescription toEntity(PrescriptionDTO dto) {
        Prescription prescription = modelMapper.map(dto, Prescription.class);
        return prescription;
    }

}