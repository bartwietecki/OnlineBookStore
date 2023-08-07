package com.onlinebookstore.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class BookModel {
    private Long id;

    @NotBlank(message = "Title cannot be blank")
    private String title;

    @NotBlank(message = "Description cannot be blank")
    private String description;

    private BigDecimal price;
    private String imageName;
    private LocalDateTime createDate;
    private Long categoryId;
    private Long authorId;

    private String categoryName;
    private String authorName;
    private String authorSurname;
}