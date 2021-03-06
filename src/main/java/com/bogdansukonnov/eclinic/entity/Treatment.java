package com.bogdansukonnov.eclinic.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

import javax.persistence.*;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@NoArgsConstructor
@Table(name = "treatment")
public class Treatment extends AbstractEntity implements SelectorData {

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    @NonNull
    private TreatmentType type;

    @Column(name = "name", unique = true)
    @NonNull
    private String name;

    @Override
    public String getSelectorText() {
        return name;
    }
}
