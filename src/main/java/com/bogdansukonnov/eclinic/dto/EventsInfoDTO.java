package com.bogdansukonnov.eclinic.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class EventsInfoDTO {

    private Long messageId;
    private boolean fullUpdate;
    private List<EventInfoDTO> events = new ArrayList<>();

    @Getter
    @Setter
    public static class EventInfoDTO {
        private Long eventId;
        private boolean show;
        private String time;
        private String treatmentType;
        private String treatment;
        private String patient;
    }

}
