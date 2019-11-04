package com.bogdansukonnov.eclinic.controller;

import com.bogdansukonnov.eclinic.security.UserPrincipal;
import com.bogdansukonnov.eclinic.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @RequestMapping("/")
    public String welcome(Authentication auth) {
        String redirect = "";
        if (auth == null) {
            redirect = "login";
        }
        else {
            redirect = userService.defaultPage((UserPrincipal) auth.getPrincipal());
        }
        return redirect;
    }

    @RequestMapping("/admin/addUser")
    public String addUserPage() {
        return "addUser";
    }

    @PostMapping("/admin/addUser")
    public void addUser(@RequestParam("username") String username, @RequestParam("password") String password) {
        userService.addUser(username, password);
    }

}
