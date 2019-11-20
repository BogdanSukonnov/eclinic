package com.bogdansukonnov.eclinic.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EventInfoDto {

    private Long eventId;
    private String time;
    private String treatmentType;
    private String treatment;
    private String patient;

}
