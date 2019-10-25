package com.bogdansukonnov.eclinic.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PrescriptionsTableDTO {

    private List<PrescriptionDTO> data;

}
