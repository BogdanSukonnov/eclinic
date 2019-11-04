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
@Table(name = "time_pattern")
public class TimePattern extends AbstractEntity implements SelectorData {

    @Column(name = "name")
    @NonNull
    private String name;

    //days, always 7 for weekCycle
    @Column(name = "cycle_length")
    @NonNull
    private Short cycleLength;

    @Column(name = "is_week_cycle")
    private Boolean isWeekCycle;

    @OneToMany(targetEntity = TimePatternItem.class
            , mappedBy = "timePattern"
            , cascade = CascadeType.ALL
            , fetch = FetchType.EAGER)
    @OrderBy("dayOfCycle, time")
    private List<TimePatternItem> items;

    @Override
    public String getSelectorText() {
        return name;
    }
}
