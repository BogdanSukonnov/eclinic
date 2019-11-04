package com.bogdansukonnov.eclinic.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class TimePatternDTO extends AbstractDTO {

    @NotNull
    private String name;

    @NotNull
    private Short cycleLength;

    @NotNull
    private Boolean isWeekCycle;

}
