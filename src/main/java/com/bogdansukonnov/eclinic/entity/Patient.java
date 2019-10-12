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

    @Column(name = "fullName")
    @NonNull
    private String fullName;

    @Column(name = "insuranceNumber")
    private String insuranceNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "patientStatus")
    @NonNull
    private PatientStatus patientStatus;

}