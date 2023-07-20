package com.onlinebookstore.service;

import com.onlinebookstore.entity.Author;
import com.onlinebookstore.entity.Book;
import com.onlinebookstore.entity.Category;
import com.onlinebookstore.model.BookModel;
import com.onlinebookstore.repository.AuthorRepository;
import com.onlinebookstore.repository.BookRepository;
import com.onlinebookstore.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository, CategoryRepository categoryRepository,
                       AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
        this.authorRepository = authorRepository;
    }

//    public List<Book> getAllBooks() {
//        return bookRepository.findAll();
//    }
    
    public List<BookModel> getAllBookModels() {
        List<Book> books = bookRepository.findAll();
        return convertToBookModels(books);
    }

//    public Book getBookById(Long bookId) throws IllegalArgumentException {
//        return bookRepository.findById(bookId)
//                .orElseThrow(() -> new IllegalArgumentException("Book with id " + bookId + " not found"));
//    }

    public BookModel getBookModelById(Long bookId) throws IllegalArgumentException {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book with id " + bookId + " not found"));
        return convertToBookModel(book);
    }

//    public List<Book> getBookByCategory(String categoryName) {
//        Category category = categoryRepository.findByName(categoryName);
//        return bookRepository.findByCategory(category);
//    }

    public List<BookModel> getBookModelsByCategory(String categoryName) {
        Category category = categoryRepository.findByName(categoryName);
        List<Book> books = bookRepository.findByCategory(category);
        return convertToBookModels(books);
    }

    private List<BookModel> convertToBookModels(List<Book> books) {
        List<BookModel> bookModels = new ArrayList<>();
        for (Book book : books) {
            BookModel bookModel = convertToBookModel(book);
            bookModels.add(bookModel);
        }
        return bookModels;
    }

    private BookModel convertToBookModel(Book book) {
        BookModel bookModel = new BookModel();
        bookModel.setId(book.getId());
        bookModel.setTitle(book.getTitle());
        bookModel.setDescription(book.getDescription());
        bookModel.setPrice(book.getPrice());
        bookModel.setImageName(book.getImageName());
        return bookModel;
    }

//    public void addBook(BookModel bookModel, Long categoryId, Long authorId) {
//        Category category = categoryRepository.findById(categoryId)
//                .orElseThrow(() -> new IllegalArgumentException("Category with id " + categoryId + " not found"));
//
//        Author author = authorRepository.findById(authorId)
//                .orElseThrow(() -> new IllegalArgumentException("Author with id " + authorId + " not found"));
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
//        bookRepository.save(book);
//    }

    public void addBook(BookModel bookModel, Long categoryId, Long authorId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category with id " + categoryId + " not found"));

        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new IllegalArgumentException("Author with id " + authorId + " not found"));

        Book book = new Book();
        book.setTitle(bookModel.getTitle());
        book.setDescription(bookModel.getDescription());
        book.setPrice(bookModel.getPrice());
        book.setImageName(bookModel.getImageName());
        book.setCreateDate(LocalDateTime.now()); // Ustawienie bieżącej daty utworzenia
        book.setCategory(category);
        book.setAuthor(author);

        bookRepository.save(book);
    }
}