package com.onlinebookstore.controller;

import com.onlinebookstore.entity.Book;
import com.onlinebookstore.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public String getBooks(Model model) {
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "book-list";
    }

    @GetMapping("/category/{categoryName}")
    public String getBooksByCategory(@PathVariable String categoryName, Model model) {
        List<Book> books = bookService.getBookByCategory(categoryName);
        model.addAttribute("books", books);
        return "book-list";
    }

}
