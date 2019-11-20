package com.bogdansukonnov.eclinic.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class EventInfoListDto {

    private List<EventInfoDto> events = new ArrayList<>();

}
