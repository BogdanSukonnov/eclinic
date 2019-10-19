package com.bogdansukonnov.eclinic.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "pattern")
public class Pattern extends AbstractEntity {

    @Column(name = "name")
    @NonNull
    private String name;

    //days, always 7 for weekCycle
    @Column(name = "cycleLength")
    @NonNull
    private Short cycleLength;

    @Column(name = "isWeekCycle")
    private Boolean isWeekCycle;

    @OneToMany(targetEntity = PatternItem.class, mappedBy = "pattern", cascade = CascadeType.ALL)
    private List<PatternItem> items;

}
