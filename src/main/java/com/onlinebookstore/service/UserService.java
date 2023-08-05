package com.onlinebookstore.service;

import com.onlinebookstore.entity.Role;
import com.onlinebookstore.entity.User;
import com.onlinebookstore.model.UserModel;
import com.onlinebookstore.repository.RoleRepository;
import com.onlinebookstore.repository.UserRepository;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
//    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository /*,PasswordEncoder passwordEncoder */) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
//        this.passwordEncoder = passwordEncoder;
    }

    public void registerNewUser(UserModel userModel) {
        if (userRepository.findUserByUsername(userModel.getUsername()) != null) {
            throw new IllegalArgumentException("Username is already taken");
        }

        User user = new User();
        user.setUsername(userModel.getUsername());
        user.setPassword(userModel.getPassword());
        user.setEmail(userModel.getEmail());

        Role defaultRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new IllegalArgumentException("Default role not found in the database"));
        user.setRole(defaultRole);

//       String hashedPassword = passwordEncoder.encode(user.getPassword());
//       user.setPassword(hashedPassword);

        userRepository.save(user);
    }

    public User loginUser(String username, String password) {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        if (!password.equals(user.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }

        return user;
    }

    public List<UserModel> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserModel> userModels = new ArrayList<>();

        for (User user : users) {
            UserModel userModel = new UserModel();
            userModel.setUsername(user.getUsername());
            userModel.setPassword(user.getPassword());
            userModel.setEmail(user.getEmail());
            userModels.add(userModel);
        }
        return userModels;
    }
}
