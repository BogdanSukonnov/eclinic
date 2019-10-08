package com.bogdansukonnov.eclinic.dto;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class PatientDTO extends AbstractDTO {

    private String fullName;

}
