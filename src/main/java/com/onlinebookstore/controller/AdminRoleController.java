package com.onlinebookstore.controller;

import com.onlinebookstore.model.RoleModel;
import com.onlinebookstore.service.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/roles")
public class AdminRoleController {

    private final RoleService roleService;

    public AdminRoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public String getRoles(Model model) {
        List<RoleModel> roles = roleService.getAllRoles();
        model.addAttribute("roles", roles);
        return "admin-role-list";
    }
}
