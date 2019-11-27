package com.bogdansukonnov.eclinic.dto;

import com.bogdansukonnov.eclinic.entity.PrescriptionStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class ResponsePrescriptionDto extends AbstractDto {

    private String doctorFullName;
    private ResponsePatientDto patient;
    private TimePatternDto timePattern;
    private TreatmentDto treatment;
    private String treatmentWithDosage;
    private PrescriptionStatus status;
    private String startDateFormatted;
    private String endDateFormatted;
    private String period;
    private Float dosage;
    private String dosageInfo;

}
