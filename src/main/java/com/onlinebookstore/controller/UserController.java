package com.onlinebookstore.controller;

import com.onlinebookstore.model.UserModel;
import com.onlinebookstore.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
//@RequestMapping("/register")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
}

//    @GetMapping("/register")
//    public String getRegisterPage(Model model) {
//        model.addAttribute("registerRequest", new UserModel());
//        return "register-page";
//    }

//    @GetMapping("/login")
//    public String getLoginPage(Model model) {
//        model.addAttribute("loginRequest", new UserModel());
//        return "login-page";
//    }

//    @PostMapping("/register")
//    public String register(@ModelAttribute UserModel userModel) {
//        System.out.println("register request: " + userModel);
//        UserModel registeredUser = userService.registerUser(userModel.getUsername(), userModel.getPassword(), userModel.getEmail());
//        return registeredUser == null ? "error-page" : "redicrect:/login";
//    }
//
//    @PostMapping("/login")
//    public String login(@ModelAttribute UserModel userModel, Model model) {
//        System.out.println("login request: " + userModel);
//        UserModel authenticated = userService.authenticate(userModel.getUsername(), userModel.getPassword());
//        if (authenticated != null) {
//            model.addAttribute("userName", authenticated.getUsername());
//            return "personal-page";
//        } else {
//            return "error-page";
//        }
//    }
//}
