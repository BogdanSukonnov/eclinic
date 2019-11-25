package com.bogdansukonnov.eclinic.entity;

import com.bogdansukonnov.eclinic.security.AuthorityType;
import lombok.*;
import org.springframework.lang.NonNull;

import javax.persistence.*;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "authority")
public class Authority extends AbstractEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "name", unique = true)
    @NonNull
    private AuthorityType name;

}
