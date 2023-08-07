package com.onlinebookstore.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogoutController {

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("userId");
        session.removeAttribute("username");
        session.removeAttribute("role");

        return "redirect:/login";
    }
}