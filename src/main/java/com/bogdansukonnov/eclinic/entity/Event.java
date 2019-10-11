package com.bogdansukonnov.eclinic.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "event")
public class Event extends AbstractEntity {

    @Column(name = "dateTime")
    @NonNull
    private LocalDateTime dateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", foreignKey = @ForeignKey(name = "FK_patient"))
    @NonNull
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prescription_id", foreignKey = @ForeignKey(name = "FK_prescription"))
    @NonNull
    private Prescription prescription;

    @Enumerated(EnumType.STRING)
    @Column(name = "eventStatus")
    @NonNull
    EventStatus eventStatus;

}
