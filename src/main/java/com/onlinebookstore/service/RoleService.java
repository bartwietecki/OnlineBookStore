package com.onlinebookstore.service;

import com.onlinebookstore.entity.Role;
import com.onlinebookstore.model.RoleModel;
import com.onlinebookstore.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<RoleModel> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream()
                .map(this::mapRoleToRoleModel)
                .collect(Collectors.toList());
    }

    private RoleModel mapRoleToRoleModel(Role role) {
        RoleModel roleModel = new RoleModel();
        roleModel.setId(role.getId());
        roleModel.setName(role.getName());
        return roleModel;
    }
}




