package com.bogdansukonnov.eclinic.exceptions;

public class VersionConflictException extends Exception {

    public VersionConflictException(String causeText) {
        super(causeText);
    }

}
