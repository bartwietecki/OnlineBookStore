package com.onlinebookstore.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
public class OrderModel {
    private Long id;
    private String customerFullName;
    private String customerEmail;
    private String city;
    private String zipCode;
    private String street;
    private String streetNo;
    private String homeNo;
    private BigDecimal price;
    private String orderStatus;
}
