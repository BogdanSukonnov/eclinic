package com.bogdansukonnov.eclinic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @GetMapping("/login/sign-in")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login/doLogin")
    public String doLogin() {
        return "";
    }

}
