package com.bogdansukonnov.eclinic.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class TableData<T> {

    private List<T> data;
    private Integer draw;
    private Long recordsTotal;
    private Long recordsFiltered;

}
