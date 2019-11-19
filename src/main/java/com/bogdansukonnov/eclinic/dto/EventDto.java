package com.bogdansukonnov.eclinic.dto;

import com.bogdansukonnov.eclinic.entity.EventStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class EventDto extends AbstractDto {

    // only to frontend
    private LocalDateTime dateTime;
    private String dateFormatted;
    private String timeFormatted;
    private String patientFullName;
    private String treatmentType;
    private String treatmentName;
    private String dosage;
    private String doctorFullName;
    private String cancelReason;
    private String nurseFullName;
    private String updatedDateFormatted;
    private String timePatternName;

    // both ways
    private EventStatus eventStatus;

}
