package com.bogdansukonnov.eclinic.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class PrescriptionDTO extends AbstractDTO {

    private PatientDTO patient;

    private String doctorFullName;

    private TimePatternDTO timePattern;

    private TreatmentDTO treatment;

    private Short duration;

    private String dosage;

    private String dateTimeFormatted;

}
