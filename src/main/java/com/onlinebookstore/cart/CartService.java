package com.onlinebookstore.cart;

import com.onlinebookstore.entity.Author;
import com.onlinebookstore.entity.Book;
import com.onlinebookstore.model.BookModel;
import com.onlinebookstore.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final BookRepository bookRepository;
    private final ShoppingCart shoppingCart;

    public CartService(BookRepository bookRepository, ShoppingCart shoppingCart) {
        this.bookRepository = bookRepository;
        this.shoppingCart = shoppingCart;
    }

    public List<BookModel> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(this::mapBookToBookModel)
                .collect(Collectors.toList());
    }

    public void bookOperation(Long bookId, CartOperation itemOperation) {
        Optional<Book> oBook = bookRepository.findById(bookId);
        if (oBook.isPresent()) {
            Book book = oBook.get();
            switch (itemOperation) {
                case INCREASE -> shoppingCart.addToCart(mapBookToBookModel(book));
                case DECREASE -> shoppingCart.decreaseBook(mapBookToBookModel(book));
                case REMOVE -> shoppingCart.removeAllBooks(mapBookToBookModel(book));
                default -> throw new IllegalArgumentException();
            }
        }
    }

    private BookModel mapBookToBookModel(Book book) {
        BookModel bookModel = new BookModel();
        bookModel.setId(book.getId());
        bookModel.setTitle(book.getTitle());
        bookModel.setDescription(book.getDescription());
        bookModel.setPrice(book.getPrice());
        bookModel.setImageName(book.getImageName());
        bookModel.setCreateDate(book.getCreateDate());
        bookModel.setCategoryId(book.getCategory().getId());
        bookModel.setAuthorId(book.getAuthor().getId());
        bookModel.setCategoryName(book.getCategory().getName());
        bookModel.setAuthorName(book.getAuthor().getName());
        bookModel.setAuthorSurname(book.getAuthor().getSurname());

        Author author = book.getAuthor();
        if (author != null) {
            bookModel.setAuthorName(author.getName());
            bookModel.setAuthorSurname(author.getSurname());
        }

        return bookModel;
    }
}

