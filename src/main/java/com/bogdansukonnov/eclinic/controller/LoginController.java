package com.bogdansukonnov.eclinic.controller;

import com.bogdansukonnov.eclinic.security.UserPrincipal;
import com.bogdansukonnov.eclinic.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpSession;

@Controller
@AllArgsConstructor
@RequestMapping("login")
public class LoginController {

    private UserService userService;

    @GetMapping("sign-in")
    public String loginPage() {
        return "login";
    }

    @GetMapping("sign-out")
    public String logoutPage() {
        return "login";
    }

    @GetMapping("loginFailed")
    public String loginError(Model model) {
        // ToDo: log.info("Login attempt failed");
        model.addAttribute("error", "true");
        return "login";
    }

    @GetMapping("logout")
    public String logout(SessionStatus session) {
        SecurityContextHolder.getContext().setAuthentication(null);
        session.setComplete();
        return "redirect:/";
    }

    @PostMapping("postLogin")
    public String postLogin(Model model, HttpSession session) {
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        validatePrinciple(authentication.getPrincipal());
        return userService.defaultPage();
    }

    private void validatePrinciple(Object principal) {
        if (!(principal instanceof UserPrincipal)) {
            throw new  IllegalArgumentException("Principal can not be null!");
        }
    }

}
