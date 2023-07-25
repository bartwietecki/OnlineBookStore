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
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private BigDecimal price;
    private String imageName;
    private LocalDateTime createDate;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    // TODO czy relacja jest prawidłowa?
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;
}


