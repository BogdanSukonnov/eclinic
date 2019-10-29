package com.bogdansukonnov.eclinic.converter;

import com.bogdansukonnov.eclinic.dto.EventDTO;
import com.bogdansukonnov.eclinic.dto.PrescriptionDTO;
import com.bogdansukonnov.eclinic.entity.*;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
@AllArgsConstructor
public class EventConverter {

    private ModelMapper modelMapper;

    public EventDTO toDTO(Event event) {
        EventDTO dto = modelMapper.map(event, EventDTO.class);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy");
        dto.setDateFormatted(event.getDateTime().format(formatter));
        formatter = DateTimeFormatter.ofPattern("hh:mm");
        dto.setTimeFormatted(event.getDateTime().format(formatter));
        return dto;
    }

}