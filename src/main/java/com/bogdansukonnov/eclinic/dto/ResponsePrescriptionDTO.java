package com.bogdansukonnov.eclinic.dto;

import com.bogdansukonnov.eclinic.entity.PrescriptionStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class ResponsePrescriptionDTO extends AbstractDTO {

    private String doctorFullName;
    private ResponsePatientDTO patient;
    private TimePatternDTO timePattern;
    private TreatmentDTO treatment;
    private String treatmentWithDosage;
    private PrescriptionStatus status;
    private String startDateFormatted;
    private String endDateFormatted;
    private String period;
    private String dosage;

}
