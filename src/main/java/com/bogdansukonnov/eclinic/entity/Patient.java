package com.bogdansukonnov.eclinic.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String fullName;

    private String insurance;

    @Enumerated(EnumType.ORDINAL)
    private PatientStatus patientStatus;

}
