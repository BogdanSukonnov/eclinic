package com.bogdansukonnov.eclinic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EClinicController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }


}
