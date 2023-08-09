package com.onlinebookstore.service.unit;

import com.onlinebookstore.entity.Role;
import com.onlinebookstore.entity.User;
import com.onlinebookstore.model.UserModel;
import com.onlinebookstore.repository.RoleRepository;
import com.onlinebookstore.repository.UserRepository;
import com.onlinebookstore.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, roleRepository, passwordEncoder);
    }

    @Test
    public void testRegisterNewUser() {
        // Given
        UserModel userModel = new UserModel();
        userModel.setUsername("username");
        userModel.setPassword("password");
        userModel.setEmail("user@example.com");

        Role defaultRole = new Role();
        defaultRole.setId(1L);

        when(userRepository.findByUsername(anyString())).thenReturn(null);
        when(roleRepository.findById(anyLong())).thenReturn(Optional.of(defaultRole));
        when(passwordEncoder.encode(anyString())).thenReturn("hashedPassword");

        // When
        userService.registerNewUser(userModel);

        // Then
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testLoginUser() {
        // Given
        String username = "user";
        String password = "password";

        User user = new User();
        user.setUsername("username");
        user.setPassword("hashedPassword");

        when(userRepository.findByUsername(anyString())).thenReturn(user);
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

        // When
        User loggedInUser = userService.loginUser(username, password);

        // Then
        assertEquals(user, loggedInUser);
    }

    @Test
    public void testGetAllUsers() {
        // Given
        User user1 = new User(1L, "user1", "hashedPassword1", "user1@example.com", null);
        User user2 = new User(2L, "user2", "hashedPassword2", "user2@example.com", null);

        List<User> userList = Arrays.asList(user1, user2);

        when(userRepository.findAll()).thenReturn(userList);

        // When
        List<UserModel> result = userService.getAllUsers();

        // Then
        assertEquals(2, result.size());
        assertEquals("user1", result.get(0).getUsername());
        assertEquals("hashedPassword1", result.get(0).getPassword());
        assertEquals("user1@example.com", result.get(0).getEmail());
        assertEquals("user2", result.get(1).getUsername());
        assertEquals("hashedPassword2", result.get(1).getPassword());
        assertEquals("user2@example.com", result.get(1).getEmail());
    }

    @Test
    public void testGetUserById() {
        // Given
        Long userId = 1L;
        User user = new User(userId, "username", "password", "user@example.com", null);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // When
        UserModel userModel = userService.getUserById(userId);

        // Then
        assertEquals(userId, userModel.getId());
        assertEquals(user.getUsername(), userModel.getUsername());
        assertEquals(user.getPassword(), userModel.getPassword());
        assertEquals(user.getEmail(), userModel.getEmail());
    }

    @Test
    public void testUpdateUser() {
        // Given
        UserModel userModel = new UserModel();
        userModel.setId(1L);
        userModel.setUsername("newUsername");
        userModel.setPassword("newHashedPassword");
        userModel.setEmail("new@example.com");

        User existingUser = new User(1L, "username", "password", "user@example.com", null);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(existingUser));

        // When
        userService.updateUser(userModel);

        // Then
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    public void testDeleteUser() {
        // Given
        Long userId = 1L;

        // When
        userService.deleteUser(userId);

        // Then
        verify(userRepository, times(1)).deleteById(userId);
    }
}