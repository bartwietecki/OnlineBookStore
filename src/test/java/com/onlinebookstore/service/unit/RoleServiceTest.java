package com.onlinebookstore.service.unit;

import com.onlinebookstore.entity.Role;
import com.onlinebookstore.model.RoleModel;
import com.onlinebookstore.repository.RoleRepository;
import com.onlinebookstore.service.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    private RoleService roleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        roleService = new RoleService(roleRepository);
    }

    @Test
    void getAllRoles() {
        // Given
        Role role1 = new Role(1L, "USER");
        Role role2 = new Role(2L, "ADMIN");

        List<Role> roles = Arrays.asList(role1, role2);

        when(roleRepository.findAll()).thenReturn(roles);

        // When
        List<RoleModel> result = roleService.getAllRoles();

        // Then
        assertEquals(roles.size(), result.size());
        assertEquals("USER", result.get(0).getName());
        assertEquals("ADMIN", result.get(1).getName());
    }
}