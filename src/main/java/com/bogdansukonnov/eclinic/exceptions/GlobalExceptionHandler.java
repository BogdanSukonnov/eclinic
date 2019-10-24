package com.bogdansukonnov.eclinic.exceptions;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    public String error404(Model model) {
        model.addAttribute("headerText", "Page not found");
        model.addAttribute("exceptionObj", "");
        return "errorPage";
    }

    @ExceptionHandler(Exception.class)
    public String allErrors(Exception ex, Model model) {
        model.addAttribute("headerText", "Something went wrong");
        model.addAttribute("exceptionObj", ex);
        log.error(ExceptionUtils.getStackTrace(ex));
        return "errorPage";
    }

}
