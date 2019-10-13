package com.bogdansukonnov.eclinic.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "diagnosis",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"name"}, name = "UK_authority_name")})
public class Diagnosis extends AbstractEntity {

    @Column(name = "name")
    @NonNull
    private String name;

}
