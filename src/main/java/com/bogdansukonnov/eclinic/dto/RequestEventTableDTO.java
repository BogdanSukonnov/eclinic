package com.bogdansukonnov.eclinic.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RequestEventTableDTO extends RequestTableDTO {

    @NotNull
    Boolean showCompleted;

}
