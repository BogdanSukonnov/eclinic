package com.bogdansukonnov.eclinic.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
public class RequestEventTableDTO extends RequestTableDTO {

    @NotNull
    Boolean showCompleted;

}
