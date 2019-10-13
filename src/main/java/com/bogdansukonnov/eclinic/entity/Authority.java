package com.bogdansukonnov.eclinic.entity;

import com.bogdansukonnov.eclinic.security.AuthorityType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "authority",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"name"}, name = "UK_authority_name")})
public class Authority extends AbstractEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    @NonNull
    private AuthorityType name;

}
