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
@Table(name = "patternItem")
public class PatternItem extends AbstractEntity {

    @Column(name = "time")
    @NonNull
    private LocalTime time;

    @Column(name = "dayOfCycle")
    @NonNull
    private Short dayOfCycle;

    @ManyToOne
    @JoinColumn(name="pattern_id", foreignKey = @ForeignKey(name = "FK_pattern"))
    @NonNull
    private Pattern pattern;

}
