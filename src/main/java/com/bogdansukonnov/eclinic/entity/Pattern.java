package com.bogdansukonnov.eclinic.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Pattern extends AbstractEntity {

    private String description;

    private Short cycleLength; //days, always 7 for weekCycle

    private Boolean isWeekCycle;

    @OneToMany
    private List<PatternItem> items;

}
