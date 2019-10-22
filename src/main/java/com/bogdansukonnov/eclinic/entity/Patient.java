package com.bogdansukonnov.eclinic.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "patient")
public class Patient extends AbstractEntity {

    @Column(name = "full_name", unique = true)
    @NonNull
    private String fullName;

    @Column(name = "diagnosis")
    @NonNull
    private String diagnosis;

    @Column(name = "insurance_number", unique = true)
    private String insuranceNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "patient_status")
    @NonNull
    private PatientStatus patientStatus;

}
