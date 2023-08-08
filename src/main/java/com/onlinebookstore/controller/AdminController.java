package com.onlinebookstore.controller;

import com.onlinebookstore.config.AdminConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminConfiguration adminConfiguration;

    public AdminController(AdminConfiguration adminConfiguration) {
        this.adminConfiguration = adminConfiguration;
    }

    //    @GetMapping
//    public String admin(Model model) {
//        return "admin-home";
//    }

    // method change because of , @Configuration Properites
    @GetMapping
    public String admin(Model model) {
        String username = adminConfiguration.getUsername();
        String password = adminConfiguration.getPassword();
        model.addAttribute("adminUsername", username);
        model.addAttribute("adminPassword", password);
        return "admin-home";
    }
}