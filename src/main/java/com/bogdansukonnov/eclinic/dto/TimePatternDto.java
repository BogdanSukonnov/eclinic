package com.bogdansukonnov.eclinic.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class TimePatternDto extends AbstractDto {

    @NotNull
    private String name;

    @NotNull
    private Short cycleLength;

    @NotNull
    private Boolean isWeekCycle;

    @NotEmpty
    private List<TimePatternItemDto> items;

}
