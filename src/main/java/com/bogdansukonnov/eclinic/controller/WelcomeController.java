package com.bogdansukonnov.eclinic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {

    @GetMapping("/")
    public String welcome() {
        return "login";
    }

    @GetMapping("temp-menu")
    public String tempMenu() {
        return "temp-menu";
    }

}
