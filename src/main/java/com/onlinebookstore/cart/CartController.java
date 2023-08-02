package com.onlinebookstore.cart;

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
            int cartSize = shoppingCart.getCartSize();
            model.addAttribute("cartSize", cartSize);
        return "cart";
    }
}

//    @PostMapping
//    @RequestMapping("/makeOrder")
//    public String makeOrder(OrderDto orderDto) {
//        orderService.makeOrder(orderDto, shoppingCart);
//        return "redirect:/plants/summary";
//    }



