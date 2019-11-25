package com.bogdansukonnov.eclinic.exceptions;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    public String error404(Model model, HttpServletRequest request, Exception ex, HttpServletResponse response) {
        model.addAttribute("headerText", "Page not found");
        model.addAttribute("exceptionObj", "");
        log.debug("Page not found " + ex);
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        return "errorPage";
    }

    @ExceptionHandler({PrescriptionUpdateException.class,
                    PrescriptionCreateException.class})
    public String customExceptions(Model model, Exception ex, HttpServletResponse response) {
        model.addAttribute("headerText", ex.getMessage());
        model.addAttribute("exceptionObj", "");
        log.debug(ex.getMessage());
        response.setStatus(HttpServletResponse.SC_CONFLICT);
        return "errorPage";
    }

    @ExceptionHandler(Exception.class)
    public String allErrors(Exception ex, Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("headerText", "Something went wrong");
        model.addAttribute("exceptionObj", ex);
        log.error(ExceptionUtils.getStackTrace(ex));
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return "errorPage";
    }

}
