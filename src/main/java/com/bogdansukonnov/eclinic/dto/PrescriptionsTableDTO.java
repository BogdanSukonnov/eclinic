package com.bogdansukonnov.eclinic.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class PrescriptionsTableDTO {

    private List<PrescriptionDTO> data;

    private Integer draw;

    private Integer recordsTotal;

    private Integer recordsFiltered;

}
