package com.bogdansukonnov.eclinic.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"username"}, name = "UK_user_username")
                , @UniqueConstraint(columnNames = {"fullName"}, name = "UK_user_fullName")})
public class AppUser extends AbstractEntity {

    @Column(name = "username")
    @NonNull
    private String username;

    @Column(name = "password")
    @NonNull
    private String password;

    @Column(name = "fullName")
    @NonNull
    private String fullName;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id", nullable = false
                    , foreignKey = @ForeignKey(name = "FK_user")) },
            inverseJoinColumns = { @JoinColumn(name = "authority_id", nullable = false
                    , foreignKey = @ForeignKey(name = "FK_authority")) })
    private Set<Authority> authorities = new HashSet<>();

}
