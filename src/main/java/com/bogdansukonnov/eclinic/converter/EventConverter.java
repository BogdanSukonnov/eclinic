package com.bogdansukonnov.eclinic.converter;

import com.bogdansukonnov.eclinic.config.EClinicConstants;
import com.bogdansukonnov.eclinic.dto.EventDto;
import com.bogdansukonnov.eclinic.dto.EventInfoDto;
import com.bogdansukonnov.eclinic.dto.EventToCalendarDto;
import com.bogdansukonnov.eclinic.entity.Event;
import com.bogdansukonnov.eclinic.entity.TreatmentType;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
@AllArgsConstructor
public class EventConverter {

    private ModelMapper modelMapper;

    public static String fmt(float f) {
        if (f == (long) f)
            return String.format("%d", (long) f);
        else
            return String.format("%s", f);
    }

    public EventDto toDto(Event event) {
        EventDto dto = modelMapper.map(event, EventDto.class);
        dto.setDateFormatted(event.getDateTime().format(EClinicConstants.dateFormatter));
        dto.setTimeFormatted(event.getDateTime().format(EClinicConstants.timeFormatter));
        dto.setUpdatedDateFormatted(event.getUpdatedDateTime() != null
                ? event.getUpdatedDateTime().format(EClinicConstants.dateFormatter)
                : null);
        dto.setDosage(formatDosage(event));
        return dto;
    }

    private String formatDosage(Event event) {
        String dosageStr;
        if (event.getTreatment().getType().equals(TreatmentType.MEDICINE)) {
            dosageStr = fmt(event.getDosage()) + (event.getDosageInfo() == null ? "" : " " + event.getDosageInfo());
        } else {
            dosageStr = "";
        }
        return dosageStr;
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

    public EventToCalendarDto toEventDateDto(Event event) {
        return new EventToCalendarDto(event.getTreatment().getName(),
                event.getDateTime().format(DateTimeFormatter.ISO_DATE_TIME),
                event.getDateTime().format(DateTimeFormatter.ISO_DATE_TIME),
                event.getPrescription().getId());
    }
}