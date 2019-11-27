package com.bogdansukonnov.eclinic.exceptions;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

    private static final String HEADER_TEXT = "headerText";
    private static final String EXCEPTION_OBJ_TEXT = "exceptionObj";
    private static final String ERROR_PAGE = "errorPage";

    @ExceptionHandler(NoHandlerFoundException.class)
    public String error404(Model model, HttpServletRequest request, Exception ex, HttpServletResponse response) {
        model.addAttribute(HEADER_TEXT, "Page not found");
        model.addAttribute(EXCEPTION_OBJ_TEXT, "");
        log.debug("Page not found " + ex);
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        return ERROR_PAGE;
    }

    @ExceptionHandler(VersionConflictException.class)
    public void customExceptions(Exception ex, HttpServletResponse response) {
        log.debug(ex.getMessage());
        response.setStatus(HttpStatus.CONFLICT.value());
    }

    @ExceptionHandler({PrescriptionUpdateException.class,
                    PrescriptionCreateException.class})
    public String customExceptions(Model model, Exception ex, HttpServletResponse response) {
        model.addAttribute(HEADER_TEXT, ex.getMessage());
        model.addAttribute(EXCEPTION_OBJ_TEXT, "");
        log.debug(ex.getMessage());
        response.setStatus(HttpServletResponse.SC_CONFLICT);
        return ERROR_PAGE;
    }

    @ExceptionHandler(Exception.class)
    public String allErrors(Exception ex, Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute(HEADER_TEXT, "Something went wrong");
        model.addAttribute(EXCEPTION_OBJ_TEXT, ex);
        log.error(ExceptionUtils.getStackTrace(ex));
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return ERROR_PAGE;
    }

}
