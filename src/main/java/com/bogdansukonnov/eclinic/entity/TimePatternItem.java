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
public class TimePatternItem extends AbstractEntity {

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

}
