package com.onlinebookstore.controller;

import com.onlinebookstore.config.AdminConfig;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminConfig adminConfig;

    public AdminController(AdminConfig adminConfig) {
        this.adminConfig = adminConfig;
    }

    //    @GetMapping
//    public String admin(Model model) {
//        return "admin-home";
//    }

    @GetMapping
    public String admin(Model model) {
        String username = adminConfig.getUsername();
        String password = adminConfig.getPassword();
        model.addAttribute("adminUsername", username);
        model.addAttribute("adminPassword", password);
        return "admin-home";
    }
}