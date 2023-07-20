package com.onlinebookstore.controller;

import com.onlinebookstore.entity.Author;
import com.onlinebookstore.entity.Book;
import com.onlinebookstore.entity.Category;
import com.onlinebookstore.model.BookModel;
import com.onlinebookstore.service.AuthorService;
import com.onlinebookstore.service.BookService;
import com.onlinebookstore.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

    //    @GetMapping
//    public String getBooks(Model model) {
//        List<Book> books = bookService.getAllBooks();
//        model.addAttribute("books", books);
//        return "book-list";
//    }

    @GetMapping
    public String getBooks(Model model) {
        List<BookModel> bookModels = bookService.getAllBookModels();
        model.addAttribute("books", bookModels);
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

//    @PostMapping("/save")
//    public String addBook(@ModelAttribute BookModel bookModel) {
//        Category category = categoryService.getCategoryById(bookModel.getCategoryId());
//
//        Author author = authorService.getAuthorById(bookModel.getAuthorId());
//
//        Book book = new Book();
//        book.setTitle(bookModel.getTitle());
//        book.setDescription(bookModel.getDescription());
//        book.setPrice(bookModel.getPrice());
//        book.setImageName(bookModel.getImageName());
//        book.setCreateDate(LocalDateTime.now());
//        book.setCategory(category);
//        book.setAuthor(author);
//
//        bookService.addBook(book);
//
//        return "redirect:/books/add";
//    }

    @PostMapping("/save")
    public String addBook(@ModelAttribute BookModel bookModel,
                          @RequestParam("categoryId") Long categoryId,
                          @RequestParam("authorId") Long authorId) {
        Category category = categoryService.getCategoryById(categoryId);
        Author author = authorService.getAuthorById(authorId);

        bookService.addBook(bookModel, categoryId, authorId);

        return "redirect:/books/add";
    }
}
