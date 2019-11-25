package com.bogdansukonnov.eclinic.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@NoArgsConstructor
@Table(name = "prescription")
public class Prescription extends AbstractEntity {

    @Column(name = "start_date")
    @NonNull
    private LocalDateTime startDate;

    @Column(name = "end_date")
    @NonNull
    private LocalDateTime endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    @NonNull
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    @NonNull
    private AppUser doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "time_pattern_id")
    @NonNull
    private TimePattern timePattern;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "treatment_id")
    @NonNull
    private Treatment treatment;

    // only for drugs, any text
    @Column(name = "dosage")
    @NonNull
    String dosage;

    @Enumerated(EnumType.STRING)
    @Column(name = "prescription_status")
    @NonNull
    private PrescriptionStatus status;
}
