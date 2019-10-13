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
@Table(name = "treatment",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"treatmentName"}, name = "UK_treatment_treatmentName")})
public class Treatment extends AbstractEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "treatmentType")
    @NonNull
    private TreatmentType treatmentType;

    @Column(name = "treatmentName")
    @NonNull
    private String treatmentName;

}
