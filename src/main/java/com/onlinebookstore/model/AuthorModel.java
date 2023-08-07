package com.onlinebookstore.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthorModel {
    private Long id;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Surname cannot be blank")
    private String surname;
}