package com.bogdansukonnov.eclinic.dto;

import com.bogdansukonnov.eclinic.entity.PatientStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class PatientDTO extends AbstractDTO {

    private String fullName;

    private String diagnosis;

    private String insuranceNumber;

    private PatientStatus patientStatus;

    private String formattedDate;

}
