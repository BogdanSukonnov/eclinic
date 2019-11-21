package com.bogdansukonnov.eclinic.converter;

import com.bogdansukonnov.eclinic.config.EClinicConstants;
import com.bogdansukonnov.eclinic.dto.EventDto;
import com.bogdansukonnov.eclinic.dto.EventInfoDto;
import com.bogdansukonnov.eclinic.entity.Event;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EventConverter {

    private ModelMapper modelMapper;

    public EventDto toDto(Event event) {
        EventDto dto = modelMapper.map(event, EventDto.class);
        dto.setDateFormatted(event.getDateTime().format(EClinicConstants.dateFormatter));
        dto.setTimeFormatted(event.getDateTime().format(EClinicConstants.timeFormatter));
        dto.setUpdatedDateFormatted(event.getUpdatedDateTime() != null
                ? event.getUpdatedDateTime().format(EClinicConstants.dateFormatter)
                : null);
        return dto;
    }

    public EventInfoDto toInfoDto(Event event) {
        return EventInfoDto.builder()
                .eventId(event.getId())
                .patient(event.getPatient().getFullName())
                .time(event.getDateTime().format(EClinicConstants.timeFormatter))
                .treatment(event.getTreatment().getName())
                .treatmentType(event.getTreatment().getType().toString())
                .build();
    }

}