package com.onlinebookstore.controller;

import com.onlinebookstore.model.CartBook;
import com.onlinebookstore.service.ShoppingCart;
import com.onlinebookstore.model.OrderModel;
import com.onlinebookstore.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final ShoppingCart shoppingCart;

    public OrderController(OrderService orderService, ShoppingCart shoppingCart) {
        this.orderService = orderService;
        this.shoppingCart = shoppingCart;
    }

    @PostMapping("/make")
    public String makeOrder(@ModelAttribute OrderModel orderModel) {
        List<CartBook> cartBooks = shoppingCart.getCartBooks();
        orderService.makeOrder(orderModel, cartBooks);
        shoppingCart.clearShoppingCart();
        return "redirect:/order/summary";
    }

    @GetMapping("/summary")
    public String showSummary(Model model) {
        model.addAttribute("cartSize", shoppingCart.getCartSize());
        return "summary";
    }

}