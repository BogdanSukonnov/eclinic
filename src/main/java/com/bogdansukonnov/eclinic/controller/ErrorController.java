package com.bogdansukonnov.eclinic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("error")
public class ErrorController {

    @GetMapping("access-denied")
    public String accessDenied(Model model) {
        model.addAttribute("isAccessDenied", true);
        model.addAttribute("headerText", "Access denied");
        model.addAttribute("exceptionObj", "");
        return "errorPage";
    }

    @GetMapping("unhandled")
    public String errorPage(Model model) {
        model.addAttribute("isAccessDenied", false);
        model.addAttribute("headerText", "Oops! Something went wrong.");
        model.addAttribute("exceptionObj", "");
        return "errorPage";
    }

}
