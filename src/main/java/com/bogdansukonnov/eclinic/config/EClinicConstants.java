package com.bogdansukonnov.eclinic.config;

import java.time.format.DateTimeFormatter;

public class EClinicConstants {

    public static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yy");
    public static final DateTimeFormatter dateNoYearFormatter = DateTimeFormatter.ofPattern("dd.MM");
    public static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    private EClinicConstants() {
    }

}
