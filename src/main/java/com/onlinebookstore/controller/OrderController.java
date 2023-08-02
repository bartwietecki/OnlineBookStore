package com.onlinebookstore.controller;

import com.onlinebookstore.cart.CartService;
import com.onlinebookstore.model.OrderModel;
import com.onlinebookstore.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;

    }

    @PostMapping("/make")
    public String makeOrder(@ModelAttribute OrderModel orderModel) {
        orderService.makeOrder(orderModel);
        return "redirect:/order/summary";
    }

    @GetMapping("/summary")
    public String showSummary() {
        return "summary";
    }

}