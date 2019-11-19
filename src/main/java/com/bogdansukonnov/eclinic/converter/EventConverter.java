package com.bogdansukonnov.eclinic.converter;

import com.bogdansukonnov.eclinic.dto.EventDto;
import com.bogdansukonnov.eclinic.dto.EventsInfoDto;
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

    public EventsInfoDto.EventInfoDto toInfoDto(Event event, boolean show) {
        EventsInfoDto.EventInfoDto eventDto = new EventsInfoDto.EventInfoDto();
        eventDto.setEventId(event.getId());
        eventDto.setShow(show);
        if (show) {
            eventDto.setPatient(event.getPatient().getFullName());
            eventDto.setTime(event.getDateTime().format(DateTimeFormatter.ofPattern("HH:mm")));
            eventDto.setTreatmentType(event.getTreatment().getType().toString());
            eventDto.setTreatment(event.getTreatment().getName());
        }
        return eventDto;
    }

}