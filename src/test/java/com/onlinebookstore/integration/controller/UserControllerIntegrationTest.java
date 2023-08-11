package com.onlinebookstore.integration.controller;

import com.onlinebookstore.model.UserModel;
import com.onlinebookstore.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetUsers_ReturnsListOfUsersAndViewAdminUserList() throws Exception {
        List<UserModel> mockUsers = new ArrayList<>();
        mockUsers.add(new UserModel(1L, "user1", "password1", "user1@example.com"));
        mockUsers.add(new UserModel(2L, "user2", "password2", "user2@example.com"));

        when(userService.getAllUsers()).thenReturn(mockUsers);

        mockMvc.perform(get("/admin/users"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin-user-list"))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attribute("users", mockUsers));
    }

    @Test
    public void testShowEditUserForm_ReturnsEditUserFormAndViewWithUserModel() throws Exception {
        UserModel mockUser = new UserModel(1L, "user1", "password1", "user1@example.com");

        when(userService.getUserById(1L)).thenReturn(mockUser);

        mockMvc.perform(get("/admin/users/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("edit-user-form"))
                .andExpect(model().attributeExists("userModel"))

                .andExpect(model().attribute("userModel", mockUser));
    }

    @Test
    public void testUpdateUser_RedirectsToAdminUsersAfterUpdatingUser() throws Exception {
        UserModel mockUser = new UserModel(1L, "user1", "password1", "user1@example.com");

        mockMvc.perform(post("/admin/users/update")
                        .param("id", "1")
                        .param("username", mockUser.getUsername())
                        .param("password", mockUser.getPassword())
                        .param("email", mockUser.getEmail()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/users"));

        verify(userService, times(1)).updateUser(mockUser);
    }

    @Test
    public void testDeleteUser_RedirectsToAdminUsersAfterDeletingUser() throws Exception {
        mockMvc.perform(post("/admin/users/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/users"));

        verify(userService, times(1)).deleteUser(1L);
    }
}