package com.bogdansukonnov.eclinic.dto;

import com.bogdansukonnov.eclinic.entity.EventStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class EventDTO extends AbstractDTO {

    private LocalDateTime dateTime;

    private String patientFullName;

    private PrescriptionDTO prescription;

    private EventStatus eventStatus;

}
