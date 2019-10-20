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
@Table(name = "timePattern")
public class TimePattern extends AbstractEntity {

    @Column(name = "name")
    @NonNull
    private String name;

    //days, always 7 for weekCycle
    @Column(name = "cycleLength")
    @NonNull
    private Short cycleLength;

    @Column(name = "isWeekCycle")
    private Boolean isWeekCycle;

    @OneToMany(targetEntity = TimePatternItem.class, mappedBy = "timePattern", cascade = CascadeType.ALL)
    private List<TimePatternItem> items;

}
