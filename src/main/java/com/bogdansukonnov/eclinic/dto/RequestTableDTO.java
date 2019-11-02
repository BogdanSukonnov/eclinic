package com.bogdansukonnov.eclinic.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RequestTableDTO {

    @NotNull
    Integer draw;

    @NotNull
    Integer offset;

    @NotNull
    Integer limit;

    String search;

    String orderField;

    String orderDirection;

}
