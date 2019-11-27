package com.bogdansukonnov.eclinic.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EventToCalendarDto {

    private String title;
    private String start;
    private String end;
    private Long prescriptionId;

}
