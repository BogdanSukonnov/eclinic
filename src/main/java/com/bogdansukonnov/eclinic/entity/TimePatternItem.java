package com.bogdansukonnov.eclinic.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "time_pattern_item")
public class TimePatternItem extends AbstractEntity implements Comparable<TimePatternItem> {

    @Column(name = "time")
    @NonNull
    private LocalTime time;

    @Column(name = "day_of_cycle")
    @NonNull
    private Short dayOfCycle;

    @ManyToOne
    @JoinColumn(name="time_pattern_id")
    @NonNull
    private TimePattern timePattern;

    @Override
    public int compareTo(TimePatternItem other) {
        if (getId().equals(other.getId())) {
            return 0;
        }
        if (dayOfCycle > other.getDayOfCycle()) {
            return 1;
        }
        else if (dayOfCycle < other.getDayOfCycle()) {
            return -1;
        }
        int timeComparison = time.compareTo(other.getTime());
        if (timeComparison != 0) {
            return timeComparison;
        }
        return timePattern.getId().compareTo(other.getTimePattern().getId());
    }
}
