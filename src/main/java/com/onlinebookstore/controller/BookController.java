package com.onlinebookstore.controller;

import com.onlinebookstore.entity.Author;
import com.onlinebookstore.entity.Category;
import com.onlinebookstore.model.BookModel;
import com.onlinebookstore.service.AuthorService;
import com.onlinebookstore.service.BookService;
import com.onlinebookstore.service.CategoryService;
import com.onlinebookstore.service.ShoppingCart;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

// ??? SLF4J ???
@Slf4j
@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final ShoppingCart shoppingCart;

    public BookController(BookService bookService, CategoryService categoryService,
                          AuthorService authorService, ShoppingCart shoppingCart) {
        this.bookService = bookService;
        this.categoryService = categoryService;
        this.authorService = authorService;

        this.shoppingCart = shoppingCart;
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

        model.addAttribute("cartSize", shoppingCart.getCartSize());

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
                          @RequestParam("authorId") Long authorId,
                          @RequestParam("file") MultipartFile file) {

        bookModel.setImageName(file.getOriginalFilename());

        bookService.addBook(bookModel, categoryId, authorId, file);

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

        model.addAttribute("cartSize", shoppingCart.getCartSize());

        return "book-details";
    }

}



