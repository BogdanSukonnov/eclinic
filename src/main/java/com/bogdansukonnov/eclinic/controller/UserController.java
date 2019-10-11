package com.bogdansukonnov.eclinic.controller;

import com.bogdansukonnov.eclinic.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @GetMapping("/admin/addUser")
    public String addUserPage() {
        return "addUser";
    }

    @PostMapping("/admin/addUser")
    public void addUser(@RequestParam("username") String username, @RequestParam("password") String password) {
        userService.addUser(username, password);
    }

}
