package com.bogdansukonnov.eclinic.entity;

import com.bogdansukonnov.eclinic.security.AuthorityType;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Authority extends AbstractEntity {

    @NonNull
    @Enumerated(EnumType.STRING)
    private AuthorityType name;

}
