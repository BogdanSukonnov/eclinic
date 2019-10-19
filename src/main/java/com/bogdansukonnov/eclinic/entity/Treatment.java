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
        uniqueConstraints = {@UniqueConstraint(columnNames = {"name"}, name = "UK_treatment_name")})
public class Treatment extends AbstractEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    @NonNull
    private TreatmentType type;

    @Column(name = "name")
    @NonNull
    private String name;

}
