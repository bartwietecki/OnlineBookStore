package com.onlinebookstore.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BookModel {
    private Long id;
    private String title;
    private String description;
    private BigDecimal price;
    private String imageName;
}
