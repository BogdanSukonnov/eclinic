package com.bogdansukonnov.eclinic.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Patient extends AbstractEntity {

    private String fullName;

    private String insuranceNumber;

    @Enumerated(EnumType.ORDINAL)
    private PatientStatus patientStatus;

}
