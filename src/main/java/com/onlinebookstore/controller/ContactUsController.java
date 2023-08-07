package com.onlinebookstore.controller;

import com.onlinebookstore.service.ShoppingCart;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContactUsController {

    private final ShoppingCart shoppingCart;

    public ContactUsController(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    @GetMapping("/contact-us")
    public String showContactUsPage(Model model) {
        model.addAttribute("cartSize", shoppingCart.getCartSize());
        return "contact-us";
    }
}