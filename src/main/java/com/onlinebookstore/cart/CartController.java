package com.onlinebookstore.cart;

import com.onlinebookstore.model.AuthorModel;
import com.onlinebookstore.model.BookModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/save")
    public String addBookToCart(@RequestParam("bookId") Long bookId, Model model) {
        cartService.bookOperation(bookId, CartOperation.INCREASE);
        model.addAttribute("books", cartService.getAllBooks());
        return "redirect:/books";
    }

    @GetMapping
    public String showCart(Model model, ShoppingCart shoppingCart) {
        model.addAttribute("books", shoppingCart.getCartBooks());
        model.addAttribute("totalCost", shoppingCart.getTotalCost() != null ? shoppingCart.getTotalCost().toString() : "0");
        int cartSize = shoppingCart.getCartSize();
        model.addAttribute("cartSize", cartSize);
        return "cart";
    }
}

//    @RequestParam(name = "bookId")
//    @PostMapping("/add")
//    public String add(@RequestParam(name = "bookId") Long bookId) {
//        BookModel bookModel = bookService.getOneBookById(bookId);
//        shoppingCart.addToCart(bookModel);
//        return "redirect:/books";
//    }
//


//    @PostMapping
//    @RequestMapping("/makeOrder")
//    public String makeOrder(OrderDto orderDto) {
//        orderService.makeOrder(orderDto, shoppingCart);
//        return "redirect:/plants/summary";
//    }



