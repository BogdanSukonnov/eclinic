package com.bogdansukonnov.eclinic.entity;

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

    @Column(name = "datetime")
    @NonNull
    private LocalDateTime dateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    @NonNull
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prescription_id")
    @NonNull
    private Prescription prescription;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_status")
    @NonNull
    EventStatus eventStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "time_pattern_id")
    @NonNull
    private TimePattern timePattern;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "treatment_id")
    @NonNull
    private Treatment treatment;

    @Column(name = "dosage")
    @NonNull
    String dosage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nurse_id")
    private AppUser nurse;

    @Column(name = "cancel_reason")
    String cancelReason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    @NonNull
    private AppUser doctor;

}
