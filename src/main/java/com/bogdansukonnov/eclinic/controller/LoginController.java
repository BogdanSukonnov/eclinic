package com.bogdansukonnov.eclinic.controller;

import com.bogdansukonnov.eclinic.security.UserPrincipal;
import com.bogdansukonnov.eclinic.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpSession;

@Controller
@AllArgsConstructor
@RequestMapping("login")
@Log4j2
public class LoginController {

    private UserService userService;
    private final String loginPage = "login";

    @RequestMapping("sign-in")
    public String loginPage() {
        return loginPage;
    }

    @RequestMapping("sign-out")
    public String logoutPage() {
        return loginPage;
    }

    @RequestMapping("loginFailed")
    public String loginError(Model model) {
        log.debug("Login attempt failed");
        model.addAttribute("error", "true");
        return loginPage;
    }

    @RequestMapping("logout")
    public String logout(SessionStatus session) {
        SecurityContextHolder.getContext().setAuthentication(null);
        session.setComplete();
        return "redirect:/";
    }

    @PostMapping("postLogin")
    public String postLogin(Model model, HttpSession session) {
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        validatePrinciple(principal);
        return userService.defaultPage(principal);
    }

    private void validatePrinciple(Object principal) {
        if (!(principal instanceof UserPrincipal)) {
            throw new  IllegalArgumentException("Principal can not be null!");
        }
    }

}
