package com.bogdansukonnov.eclinic.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public abstract class AbstractEntity {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @CreationTimestamp
    @Column(name = "created_datetime")
    private LocalDateTime createdDateTime;

    @UpdateTimestamp
    @Column(name = "updated_datetime")
    private LocalDateTime updatedDateTime;

    @Version
    @Column(name = "version")
    private Integer version;

}
