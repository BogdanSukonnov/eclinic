package com.bogdansukonnov.eclinic.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Pattern {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String pattern;

}
