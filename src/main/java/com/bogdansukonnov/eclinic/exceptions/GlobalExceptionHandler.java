package com.bogdansukonnov.eclinic.exceptions;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    public String error404(Model model) {
        model.addAttribute("headerText", "Page not found");
        model.addAttribute("exceptionObj", "2");
        return "errorPage";
    }

    @ExceptionHandler(Throwable.class)
    public String allErrors(Throwable ex, Model model) {
        model.addAttribute("headerText", "Something went wrong");
        model.addAttribute("exceptionObj", ex);
        return "errorPage";
    }

}
