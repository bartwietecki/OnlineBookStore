package com.onlinebookstore.service;

import com.onlinebookstore.entity.Book;
import com.onlinebookstore.entity.Category;
import com.onlinebookstore.repository.BookRepository;
import com.onlinebookstore.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    public BookService(BookRepository bookRepository, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long bookId) throws IllegalArgumentException {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book with id " + bookId + " not found"));
    }

    public List<Book> getBookByCategory(String categoryName) {
        Category category = categoryRepository.findByName(categoryName);
        return bookRepository.findByCategory(category);
    }
}