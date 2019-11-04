package com.bogdansukonnov.eclinic.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@EqualsAndHashCode
public abstract class AbstractDTO {

    @NotNull(groups = Update.class)
    private Long id;

    @NotNull(groups = Update.class)
    private Integer version;

}
