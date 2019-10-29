package com.bogdansukonnov.eclinic.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TableDataDTO<T> {

    private List<T> data;

    private Integer draw;

    private Integer recordsTotal;

    private Integer recordsFiltered;

}
