package com.bogdansukonnov.eclinic.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class TreatmentDto extends AbstractDto {

    private String name;

    private String type;

}
