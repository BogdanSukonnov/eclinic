package com.bogdansukonnov.eclinic.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PatternItem extends AbstractEntity {

    private LocalTime time;

    private Short dayOfCycle;

}
