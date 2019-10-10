package com.bogdansukonnov.eclinic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login/sign-in")
    public String sighnIn() {
        return "sign-in";
    }
}
