package com.onlinebookstore.controller;

import com.onlinebookstore.model.UserModel;
import com.onlinebookstore.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userModel", new UserModel());
        return "registration-form";
    }

    @PostMapping("/register")
    public String processRegistrationForm(@ModelAttribute("userModel") UserModel userModel) {
        userService.registerNewUser(userModel);
        return "redirect:/login";
    }
}
//    @PostMapping("/register")
//    public String processRegistrationForm(@ModelAttribute("userModel") UserModel userModel) {
//        // Map UserModel to User entity
//        User user = new User();
//        user.setUsername(userModel.getUsername());
//        user.setPassword(userModel.getPassword());
//        user.setEmail(userModel.getEmail());
//
//        // Set default role (assuming the "USER" role exists in the database)
//        Role defaultRole = new Role(); // Assuming Role entity has a constructor
//        defaultRole.setId(1L); // Assuming the "USER" role has an ID of 1 in the database
//        user.setRole(defaultRole);
//
//        userService.registerNewUser(user);
//
//        return "redirect:/login";
//    }