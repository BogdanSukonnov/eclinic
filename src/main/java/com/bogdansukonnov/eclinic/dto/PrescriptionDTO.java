package com.bogdansukonnov.eclinic.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class PrescriptionDTO extends AbstractDTO {

    // only to frontend
    private String dateTimeFormatted;
    private String doctorFullName;
    private PatientDTO patient;
    private TimePatternDTO timePattern;
    private TreatmentDTO treatment;

    // both ways
    @NotNull
    private Short duration;
    @NotNull
    private String dosage;

    // from from frontend
    @NotNull
    private Long patientId;
    @NotNull
    private Long timePatternId;
    @NotNull
    private Long treatmentId;

}
