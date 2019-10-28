package com.bogdansukonnov.eclinic.converter;

import com.bogdansukonnov.eclinic.dto.SelectorData;
import com.bogdansukonnov.eclinic.dto.SelectorDataDTO;
import com.bogdansukonnov.eclinic.dto.SelectorDataElementDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SelectorDataConverter {

    public SelectorDataDTO toDTO(List<SelectorData> data) {
        SelectorDataDTO dto = new SelectorDataDTO();
        dto.setResults(data.stream()
                .map(d -> {
                    SelectorDataElementDTO element = new SelectorDataElementDTO();
                    element.setId(d.getId());
                    element.setText(d.getSelectorText());
                    return element;
                })
                .collect(Collectors.toList()));
        return dto;
    }

}
