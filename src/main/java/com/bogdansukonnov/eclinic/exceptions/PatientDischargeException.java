package com.bogdansukonnov.eclinic.exceptions;

public class PatientDischargeException extends Exception {

    public PatientDischargeException(String causeText) {
        super(causeText);
    }

}
