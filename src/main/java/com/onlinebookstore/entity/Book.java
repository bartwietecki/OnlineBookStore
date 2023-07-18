package com.onlinebookstore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private BigDecimal price;

    private String imageName;

    private LocalDateTime createDate;

    // TODO implementation of Category entity
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    // TODO implementation of Author entity
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;
}
