package com.bogdansukonnov.eclinic.converter;

import com.bogdansukonnov.eclinic.dto.EventDto;
import com.bogdansukonnov.eclinic.dto.EventInfoDto;
import com.bogdansukonnov.eclinic.entity.Event;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
@AllArgsConstructor
public class EventConverter {

    private ModelMapper modelMapper;
    private DateFormatter dateFormatter;

    public EventDto toDto(Event event) {
        EventDto dto = modelMapper.map(event, EventDto.class);
        dto.setDateFormatted(event.getDateTime().format(dateFormatter.date()));
        dto.setTimeFormatted(event.getDateTime().format(dateFormatter.time()));
        dto.setUpdatedDateFormatted(event.getUpdatedDateTime() != null
                ? event.getUpdatedDateTime().format(dateFormatter.date())
                : null);
        return dto;
    }

    public EventInfoDto toInfoDto(Event event) {
        return EventInfoDto.builder()
                .eventId(event.getId())
                .patient(event.getPatient().getFullName())
                .time(event.getDateTime().format(DateTimeFormatter.ofPattern("HH:mm")))
                .treatment(event.getTreatment().getName())
                .treatmentType(event.getTreatment().getType().toString())
                .build();
    }

}