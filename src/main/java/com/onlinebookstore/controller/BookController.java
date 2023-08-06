package com.onlinebookstore.controller;

import com.onlinebookstore.model.BookModel;
import com.onlinebookstore.service.BookService;
import com.onlinebookstore.service.ShoppingCart;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final ShoppingCart shoppingCart;

    public BookController(BookService bookService, ShoppingCart shoppingCart) {
        this.bookService = bookService;
        this.shoppingCart = shoppingCart;
    }

    @GetMapping
    public String getBooks(@RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "9") int size,
                           @RequestParam(required = false) String keyword,
                           Model model) {

        if(keyword != null && !keyword.isEmpty()) {
            Page<BookModel> pagedBooks = bookService.getBooksByKeyword(keyword, page, size);
            model.addAttribute("pagedBooks", pagedBooks);
            model.addAttribute("keyword", keyword);
        } else {
            Page<BookModel> pagedBooks = bookService.getAllBooksPaged(page, size);
            model.addAttribute("pagedBooks", pagedBooks);
        }

        model.addAttribute("cartSize", shoppingCart.getCartSize());

        return "book-list";
    }

    @GetMapping("/details/{bookId}")
    public String viewBookDetails(@PathVariable("bookId") Long bookId, Model model) {
        Map<String, Object> bookData = bookService.getBookById(bookId);

        BookModel bookModel = (BookModel) bookData.get("book");
        String categoryName = (String) bookData.get("categoryName");
        String authorName = (String) bookData.get("authorName");

        model.addAttribute("book", bookModel);
        model.addAttribute("categoryName", categoryName);
        model.addAttribute("authorName", authorName);

        model.addAttribute("cartSize", shoppingCart.getCartSize());

        return "book-details";
    }
}



