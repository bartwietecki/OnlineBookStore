package com.onlinebookstore.controller;

import com.onlinebookstore.model.UserModel;
import com.onlinebookstore.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {

    private final UserService userService;

    public AdminUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getUsers(Model model) {
        List<UserModel> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin-user-list";
    }

    @GetMapping("/edit/{id}")
    public String showEditUserForm(@PathVariable Long id, Model model) {
        UserModel userModel = userService.getUserById(id);
        model.addAttribute("userModel", userModel);
        return "edit-user-form";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute UserModel userModel) {
        userService.updateUser(userModel);
        return "redirect:/admin/users";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }
}