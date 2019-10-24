package com.bogdansukonnov.eclinic.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class PrescriptionDTO extends AbstractDTO {

    private PatientDTO patient;

    private Long patientId;

    private String doctorFullName;

    private String patternName;

    private Long patternId;

    private String treatmentName;

    private Long treatmentId;

    private String treatmentType;

    private Short duration;

    private String dosage;

    private String dateTimeFormatted;

}
