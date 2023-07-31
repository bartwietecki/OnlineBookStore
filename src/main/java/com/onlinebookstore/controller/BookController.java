package com.onlinebookstore.controller;

import com.onlinebookstore.entity.Author;
import com.onlinebookstore.entity.Category;
import com.onlinebookstore.model.BookModel;
import com.onlinebookstore.service.AuthorService;
import com.onlinebookstore.service.BookService;
import com.onlinebookstore.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final CategoryService categoryService;
    private final AuthorService authorService;

    public BookController(BookService bookService, CategoryService categoryService, AuthorService authorService) {
        this.bookService = bookService;
        this.categoryService = categoryService;
        this.authorService = authorService;
    }

    // użycie BookModel zamiast Book - wywołanie książek bez paginacji

//    @GetMapping
//    public String getBooks(Model model) {
//        List<BookModel> bookModels = bookService.getAllBooks();
//        model.addAttribute("books", bookModels);
//        return "book-list";
//    }

//     metoda wywołania książek z paginacją
//    @GetMapping
//    public String getBooks(@RequestParam(defaultValue = "0") int page,
//                           @RequestParam(defaultValue = "9") int size,
//                           Model model) {
//        Page<BookModel> pagedBooks = bookService.getAllBooksPaged(page, size);
//
//        model.addAttribute("pagedBooks", pagedBooks);
//
//        return "book-list";
//    }

    @GetMapping("/category/{categoryName}")
    public String getBooksByCategory(@PathVariable String categoryName, Model model) {
        List<BookModel> bookModels = bookService.getBookModelsByCategory(categoryName);
        model.addAttribute("books", bookModels);
        return "book-list";
    }

    @GetMapping("/add")
    public String showAddBookForm(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        List<Author> authors = authorService.getAllAuthors();
        model.addAttribute("bookModel", new BookModel());
        model.addAttribute("categories", categories);
        model.addAttribute("authors", authors);
        return "add-book-form";
    }

    @PostMapping("/save")
    public String addBook(@ModelAttribute BookModel bookModel,
                          @RequestParam("categoryId") Long categoryId,
                          @RequestParam("authorId") Long authorId) {

        bookService.addBook(bookModel, categoryId, authorId);

        return "redirect:/books/add";
    }

        @GetMapping("/details/{bookId}")
    public String viewBookDetails(@PathVariable("bookId") Long bookId, Model model) {
        // get Book by id from service
        BookModel bookModel = bookService.getBookById(bookId);

        model.addAttribute("book", bookModel);

        return "book-details";
    }


    // PAGINATION + AJAX SEARCH FUNCTIONALITY

//    @GetMapping
//    public String getBooks(@RequestParam(defaultValue = "0") int page,
//                           @RequestParam(defaultValue = "9") int size,
//                           Model model, String keyword) {
//        Page<BookModel> pagedBooks = bookService.getAllBooksPaged(page, size);
//
//        if(keyword != null) {
//            model.addAttribute("pagedBooks", bookService.getBooksByKeyword(keyword));
//        } else {
//            model.addAttribute("pagedBooks", pagedBooks);
//        }
//
//        return "book-list";
//    }

    @GetMapping
    public String getBooks(@RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "9") int size,
                           @RequestParam(required = false) String keyword,
                           Model model) {

        if(keyword != null && !keyword.isEmpty()) {
            model.addAttribute("pagedBooks", bookService.getBooksByKeyword(keyword));
        } else {
            Page<BookModel> pagedBooks = bookService.getAllBooksPaged(page, size);
            model.addAttribute("pagedBooks", pagedBooks);
        }

        return "book-list";
    }


}



