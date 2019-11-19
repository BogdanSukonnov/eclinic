package com.bogdansukonnov.eclinic.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class TableDataDto<T> {

    private List<T> data;
    private Integer draw;
    private Long recordsTotal;
    private Long recordsFiltered;

}
