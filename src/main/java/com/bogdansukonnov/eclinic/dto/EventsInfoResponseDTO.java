package com.bogdansukonnov.eclinic.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Getter
@Setter
public class EventsInfoResponseDTO {

    private final int control = 1 + new Random().nextInt(10000);
    private boolean fullUpdate;
    private List<EventInfoDTO> events = new ArrayList<>();

    @Getter
    @Setter
    public static class EventInfoDTO {
        private Long id;
        private boolean show;
        private String time;
        private String treatmentType;
        private String treatment;
        private String patient;
    }

}
