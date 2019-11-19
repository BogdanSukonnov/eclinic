package com.bogdansukonnov.eclinic.converter;

import com.bogdansukonnov.eclinic.dto.SelectorDataDto;
import com.bogdansukonnov.eclinic.dto.SelectorDataElementDto;
import com.bogdansukonnov.eclinic.entity.SelectorData;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SelectorDataConverter {

    public SelectorDataDto toDto(List<SelectorData> data) {
        SelectorDataDto dto = new SelectorDataDto();
        dto.setResults(data.stream()
                .map(d -> {
                    SelectorDataElementDto element = new SelectorDataElementDto();
                    element.setId(d.getId());
                    element.setText(d.getSelectorText());
                    return element;
                })
                .collect(Collectors.toList()));
        return dto;
    }

}
