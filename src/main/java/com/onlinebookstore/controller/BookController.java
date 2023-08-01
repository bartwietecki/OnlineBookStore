package com.onlinebookstore.controller;

import com.onlinebookstore.cart.CartOperation;
import com.onlinebookstore.cart.CartService;
import com.onlinebookstore.cart.ShoppingCart;
import com.onlinebookstore.entity.Author;
import com.onlinebookstore.entity.Book;
import com.onlinebookstore.entity.Category;
import com.onlinebookstore.model.BookModel;
import com.onlinebookstore.service.AuthorService;
import com.onlinebookstore.service.BookService;
import com.onlinebookstore.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final CategoryService categoryService;
    private final AuthorService authorService;

    // shopping cart
    private final CartService cartService;

    public BookController(BookService bookService, CategoryService categoryService,
                          AuthorService authorService, CartService cartService) {
        this.bookService = bookService;
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.cartService = cartService;
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

        return "book-list";
    }

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

//        @GetMapping("/details/{bookId}")
//    public String viewBookDetails(@PathVariable("bookId") Long bookId, Model model) {
//        // get Book by id from service
//        BookModel bookModel = bookService.getBookById(bookId);
//
//        model.addAttribute("book", bookModel);
//
//        return "book-details";
//    }

    @GetMapping("/details/{bookId}")
    public String viewBookDetails(@PathVariable("bookId") Long bookId, Model model) {
        Map<String, Object> bookData = bookService.getBookById(bookId);

        BookModel bookModel = (BookModel) bookData.get("book");
        String categoryName = (String) bookData.get("categoryName");
        String authorName = (String) bookData.get("authorName");

        model.addAttribute("book", bookModel);
        model.addAttribute("categoryName", categoryName);
        model.addAttribute("authorName", authorName);

        return "book-details";
    }

}



