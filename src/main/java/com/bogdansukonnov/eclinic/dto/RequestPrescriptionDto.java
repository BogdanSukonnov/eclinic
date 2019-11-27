package com.bogdansukonnov.eclinic.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class RequestPrescriptionDto extends AbstractDto {

    @NotNull
    private Float dosage;
    private String dosageInfo;
    @NotNull
    private Long patientId;
    @NotNull
    private Long timePatternId;
    @NotNull
    private Long treatmentId;

}
