package com.onlinebookstore.controller;

import com.onlinebookstore.model.AuthorModel;
import com.onlinebookstore.model.BookModel;
import com.onlinebookstore.model.CategoryModel;
import com.onlinebookstore.service.AuthorService;
import com.onlinebookstore.service.BookService;
import com.onlinebookstore.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/admin/books")
public class AdminBookController {

    private final BookService bookService;
    private final CategoryService categoryService;
    private final AuthorService authorService;

    public AdminBookController(BookService bookService, CategoryService categoryService, AuthorService authorService) {
        this.bookService = bookService;
        this.categoryService = categoryService;
        this.authorService = authorService;
    }

    @GetMapping
    public String getBooks(Model model) {
        List<BookModel> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "admin-book-list";
    }

    @GetMapping("/add")
    public String showAddBookForm(Model model) {
        List<CategoryModel> categories = categoryService.getAllCategories();
        List<AuthorModel> authors = authorService.getAllAuthors();
        model.addAttribute("bookModel", new BookModel());
        model.addAttribute("categories", categories);
        model.addAttribute("authors", authors);
        return "add-book-form";
    }

    @PostMapping("/save")
    public String addBook(@ModelAttribute BookModel bookModel,
                          @RequestParam("categoryId") Long categoryId,
                          @RequestParam("authorId") Long authorId,
                          @RequestParam("file") MultipartFile file) {

        bookModel.setImageName(file.getOriginalFilename());

        bookService.addBook(bookModel, categoryId, authorId, file);

        return "redirect:/admin/books";
    }

    @GetMapping("/edit/{bookId}")
    public String showEditBookForm(@PathVariable("bookId") Long bookId, Model model) {
        Map<String, Object> bookData = bookService.getBookById(bookId);

        BookModel bookModel = (BookModel) bookData.get("book");
        List<CategoryModel> categories = categoryService.getAllCategories();
        List<AuthorModel> authors = authorService.getAllAuthors();

        model.addAttribute("bookModel", bookModel);
        model.addAttribute("categories", categories);
        model.addAttribute("authors", authors);

        return "edit-book-form";
    }

    @PostMapping("/edit")
    public String updateBook(@ModelAttribute BookModel bookModel,
                             @RequestParam("categoryId") Long categoryId,
                             @RequestParam("authorId") Long authorId) {

        bookService.updateBook(bookModel, categoryId, authorId);

        return "redirect:/admin/books";
    }

    @PostMapping("/delete/{bookId}")
    public String deleteBook(@PathVariable("bookId") Long bookId) {
        bookService.deleteBook(bookId);
        return "redirect:/admin/books";
    }
}
