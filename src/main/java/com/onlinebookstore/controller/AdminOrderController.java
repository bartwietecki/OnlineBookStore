package com.onlinebookstore.controller;

import com.onlinebookstore.model.OrderModel;
import com.onlinebookstore.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/orders")
public class AdminOrderController {

    private final OrderService orderService;

    public AdminOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public String getOrders(Model model) {
        List<OrderModel> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "admin-order-list";
    }

    @GetMapping("/edit/{id}")
    public String showEditOrderForm(@PathVariable Long id, Model model) {
        OrderModel order = orderService.getOrderById(id);
        model.addAttribute("order", order);
        return "edit-order-form";
    }

    @PostMapping("/edit/{id}")
//    @PostMapping("/update")
    public String updateOrder(@PathVariable Long id, @ModelAttribute OrderModel updatedOrderModel) {
        orderService.updateOrder(id, updatedOrderModel);
        return "redirect:/admin/orders";
    }

    @PostMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return "redirect:/admin/orders";
    }
}