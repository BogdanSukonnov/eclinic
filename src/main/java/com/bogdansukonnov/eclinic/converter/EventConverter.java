package com.bogdansukonnov.eclinic.converter;

import com.bogdansukonnov.eclinic.dto.EventDTO;
import com.bogdansukonnov.eclinic.entity.Event;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EventConverter {

    private ModelMapper modelMapper;
    private DateFormatter dateFormatter;

    public EventDTO toDTO(Event event) {
        EventDTO dto = modelMapper.map(event, EventDTO.class);
        dto.setDateFormatted(event.getDateTime().format(dateFormatter.date()));
        dto.setTimeFormatted(event.getDateTime().format(dateFormatter.time()));
        dto.setUpdatedDateFormatted(event.getUpdatedDateTime() != null
                ? event.getUpdatedDateTime().format(dateFormatter.date())
                : null);
        return dto;
    }

}