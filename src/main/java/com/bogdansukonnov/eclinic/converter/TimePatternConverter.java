package com.bogdansukonnov.eclinic.converter;

import com.bogdansukonnov.eclinic.dto.TimePatternDTO;
import com.bogdansukonnov.eclinic.entity.TimePattern;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TimePatternConverter {

    private ModelMapper modelMapper;

    public TimePatternDTO toDTO(TimePattern timePattern) {
        return modelMapper.map(timePattern, TimePatternDTO.class);
    }

    public TimePattern toEntity(TimePatternDTO dto) {
        return modelMapper.map(dto, TimePattern.class);
    }

}
