package com.bogdansukonnov.eclinic.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class Control {

    private volatile int control;

}
