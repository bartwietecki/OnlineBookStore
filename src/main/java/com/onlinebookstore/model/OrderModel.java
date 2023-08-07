package com.onlinebookstore.model;

import com.onlinebookstore.entity.Book;
import com.onlinebookstore.entity.OrderStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderModel {
    private Long id;

    @NotBlank(message = "Customer full name cannot be blank")
    private String customerFullName;

    @NotBlank(message = "Customer email cannot be blank")
    @Email(message = "Invalid email format")
    private String customerEmail;

    private String city;
    private String zipCode;
    private String street;
    private String streetNo;
    private String homeNo;
    private BigDecimal price;
    private OrderStatus orderStatus;


    private List<Book> books;
}