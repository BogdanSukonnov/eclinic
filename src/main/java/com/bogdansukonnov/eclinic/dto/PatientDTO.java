package com.bogdansukonnov.eclinic.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class PatientDTO {

    @Nullable
    private long id;

    private String fullName;

}
