package com.onlinebookstore.controller;

import com.onlinebookstore.service.CartService;
import com.onlinebookstore.service.ShoppingCart;
import com.onlinebookstore.entity.CartOperation;
import com.onlinebookstore.model.OrderModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final ShoppingCart shoppingCart;

    public CartController(CartService cartService, ShoppingCart shoppingCart) {
        this.cartService = cartService;
        this.shoppingCart = shoppingCart;
    }

    @PostMapping("/save")
    public String addBookToCart(@RequestParam("bookId") Long bookId, Model model) {
        cartService.bookOperation(bookId, CartOperation.INCREASE);
        model.addAttribute("books", cartService.getAllBooks());
        return "redirect:/books";
    }

    @GetMapping
    public String showCart(Model model) {
        model.addAttribute("books", shoppingCart.getCartBooks());
        model.addAttribute("totalCost", shoppingCart.getTotalCost() != null ? shoppingCart.getTotalCost().toString() : "0");
        model.addAttribute("orderModel", new OrderModel());
        model.addAttribute("cartSize", shoppingCart.getCartSize());
        return "cart";
    }

    @GetMapping("/increase/{bookId}")
    public String increaseBook(@PathVariable("bookId") Long bookId) {
        cartService.bookOperation(bookId, CartOperation.INCREASE);
        return "redirect:/cart";
    }

    @GetMapping("/decrease/{bookId}")
    public String decreaseBook(@PathVariable("bookId") Long bookId) {
        cartService.bookOperation(bookId, CartOperation.DECREASE);
        return "redirect:/cart";
    }

    @GetMapping("/remove/{bookId}")
    public String removeBooksFromCart(@PathVariable("bookId") Long bookId) {
        cartService.bookOperation(bookId, CartOperation.REMOVE);
        return "redirect:/cart";
    }
}






