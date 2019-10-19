package com.bogdansukonnov.eclinic.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
public abstract class AbstractDTO {

    private Long id;

    private LocalDateTime createdDateTime;

}
