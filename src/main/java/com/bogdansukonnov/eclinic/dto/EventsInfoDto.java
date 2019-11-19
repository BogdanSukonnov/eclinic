package com.bogdansukonnov.eclinic.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class EventsInfoDto {

    private Long messageId;
    private boolean fullUpdate;
    private List<EventInfoDto> events = new ArrayList<>();

    @Getter
    @Setter
    public static class EventInfoDto {
        private Long eventId;
        private boolean show;
        private String time;
        private String treatmentType;
        private String treatment;
        private String patient;
    }

}
