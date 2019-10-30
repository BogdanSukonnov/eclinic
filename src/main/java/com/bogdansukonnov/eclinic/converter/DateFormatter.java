package com.bogdansukonnov.eclinic.converter;

import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class DateFormatter {

    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yy");
    private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public DateTimeFormatter date() {
        return dateFormatter;
    }

    public DateTimeFormatter time() {
        return timeFormatter;
    }

}
