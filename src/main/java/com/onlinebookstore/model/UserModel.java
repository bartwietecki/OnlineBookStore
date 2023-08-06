package com.onlinebookstore.model;

import com.onlinebookstore.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class UserModel {
    private Long id;
    private String username;
    private String password;
    private String email;
}
