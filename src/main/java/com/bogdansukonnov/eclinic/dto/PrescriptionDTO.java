package com.bogdansukonnov.eclinic.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class PrescriptionDTO extends AbstractDTO {

    private PatientDTO patient;

    private String doctorFullName;

    private String patternName;

    private Long patternId;

    private String treatmentName;

    private Long treatmentId;

    private String treatmentType;

    private Short duration;

    private Float dosage;

    private LocalDateTime creationDateTime;

}
