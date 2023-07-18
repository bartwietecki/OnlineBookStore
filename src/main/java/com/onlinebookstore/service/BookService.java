package com.onlinebookstore.service;

import com.onlinebookstore.entity.Book;
import com.onlinebookstore.entity.Category;
import com.onlinebookstore.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long bookId) throws IllegalArgumentException {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book with id " + bookId + " not found"));
    }

//    public List<Book> getBookByCategory(String category) {
//        Category category = categoryRepository.findByName(categoryName);
//        List<Book> books = bookRepository.findByCategory(category);
//
//        return bookRepository.findByCategory(category);
//    }
}