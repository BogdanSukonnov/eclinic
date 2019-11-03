package com.bogdansukonnov.eclinic.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class RequestPrescriptionDTO extends AbstractDTO {

    @NotNull
    private String dosage;
    @NotNull
    private Long patientId;
    @NotNull
    private Long timePatternId;
    @NotNull
    private Long treatmentId;

}
