package com.onlinebookstore.controller;

import com.onlinebookstore.entity.User;
import com.onlinebookstore.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login-form";
    }

    @PostMapping("/login")
    public String processLoginForm(@RequestParam("username") String username,
                                   @RequestParam("password") String password,
                                   HttpSession session) {
        User user = userService.loginUser(username, password);

        String roleName = user.getRole().getName();

        session.setAttribute("userId", user.getId());
        session.setAttribute("username", user.getUsername());
        session.setAttribute("role", roleName);

        return "redirect:/books";
    }
}