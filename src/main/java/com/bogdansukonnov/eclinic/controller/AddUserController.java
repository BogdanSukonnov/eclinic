package com.bogdansukonnov.eclinic.controller;

import com.bogdansukonnov.eclinic.service.AddUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class AddUserController {

    @NonNull
    private AddUserService addUserService;

    @GetMapping("/admin/addUser")
    public String addUserPage() {
        return "addUser";
    }

    @PostMapping("/admin/addUser")
    public void addUser(@RequestParam("username") String username, @RequestParam("password") String password) {
        addUserService.addUser(username, password);
    }

}
