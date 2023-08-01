package com.onlinebookstore.cart;

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

    @GetMapping("/add/{bookId}")
    public String addBookToCart(@PathVariable("bookId") Long itemId, Model model) {
        cartService.bookOperation(itemId, CartOperation.INCREASE);
        model.addAttribute("items", cartService.getAllBooks());
        return "book-list";
    }

}


    //@RequestParam(name = "plantId") nazwa plantId musi pokrywać się z tym co jest w atrybucie name na html
//    @PostMapping("/add")
//    public String add(@RequestParam(name = "bookId") Long bookId) {
//        BookModel bookModel = bookService.getOneBookById(bookId);
//        shoppingCart.addToCart(bookModel);
//        return "redirect:/books";
//    }
//
//    @GetMapping
//    public String showCart(Model model) {
//        model.addAttribute("books", shoppingCart.getBooks());
//        model.addAttribute("totalCost", shoppingCart.getTotalCost() != null ? shoppingCart.getTotalCost().toString() : "0");
//        int cartSize = shoppingCart.getCartSize();
//        model.addAttribute("cartSize", cartSize);
//        return "cart";
//    }

//    @PostMapping
//    @RequestMapping("/makeOrder")
//    public String makeOrder(OrderDto orderDto) {
//        orderService.makeOrder(orderDto, shoppingCart);
//        return "redirect:/plants/summary";
//    }
