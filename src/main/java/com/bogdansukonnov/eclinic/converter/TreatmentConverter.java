package com.bogdansukonnov.eclinic.converter;

import com.bogdansukonnov.eclinic.dto.TreatmentDTO;
import com.bogdansukonnov.eclinic.entity.Treatment;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TreatmentConverter {

    private ModelMapper modelMapper;

    public TreatmentDTO toDTO(Treatment treatment) {
        return modelMapper.map(treatment, TreatmentDTO.class);
    }

    public Treatment toEntity(TreatmentDTO dto) {
        return modelMapper.map(dto, Treatment.class);
    }

}
