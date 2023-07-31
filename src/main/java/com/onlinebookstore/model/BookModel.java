package com.onlinebookstore.model;

import com.onlinebookstore.entity.Author;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class BookModel {
    private Long id;
    private String title;
    private String description;
    private BigDecimal price;
    private String imageName;
    private LocalDateTime createDate;
    private Long categoryId;
    private Long authorId;
    private String categoryName; // added categoryName to display product category in details
    private String authorName; // added authorName to display product author in details
}
