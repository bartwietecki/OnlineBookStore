package com.onlinebookstore.cart;

import com.onlinebookstore.entity.Book;
import com.onlinebookstore.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    private final BookRepository bookRepository;
    private final ShoppingCart shoppingCart;

    public CartService(BookRepository bookRepository, ShoppingCart shoppingCart) {
        this.bookRepository = bookRepository;
        this.shoppingCart = shoppingCart;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public void bookOperation(Long bookId, CartOperation itemOperation) {
        Optional<Book> oBook = bookRepository.findById(bookId);
        if (oBook.isPresent()) {
            Book book = oBook.get();
            switch (itemOperation) {
                case INCREASE -> shoppingCart.addBook(book);
                case DECREASE -> shoppingCart.decreaseBook(book);
                case REMOVE -> shoppingCart.removeAllIBooks(book);
                default -> throw new IllegalArgumentException();
            }
        }
    }

}

