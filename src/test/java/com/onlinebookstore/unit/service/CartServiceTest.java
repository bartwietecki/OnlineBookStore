package com.onlinebookstore.unit.service;

import com.onlinebookstore.entity.Author;
import com.onlinebookstore.entity.Book;
import com.onlinebookstore.entity.CartOperation;
import com.onlinebookstore.entity.Category;
import com.onlinebookstore.model.BookModel;
import com.onlinebookstore.repository.BookRepository;
import com.onlinebookstore.service.CartService;
import com.onlinebookstore.service.ShoppingCart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CartServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private ShoppingCart shoppingCart;

    private CartService cartService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cartService = new CartService(bookRepository, shoppingCart);
    }

    @Test
    public void testGetAllBooks() {
        // Given
        Book book1 = new Book(1L, "Book 1", "Description 1", BigDecimal.valueOf(25.0), "image1.jpg", LocalDateTime.now(), new Category(), new Author(), null);
        Book book2 = new Book(2L, "Book 2", "Description 2", BigDecimal.valueOf(30.0), "image2.jpg", LocalDateTime.now(), new Category(), new Author(), null);

        List<Book> books = Arrays.asList(book1, book2);

        when(bookRepository.findAll()).thenReturn(books);

        // When
        List<BookModel> result = cartService.getAllBooks();

        // Then
        assertEquals(books.size(), result.size());
        assertEquals(books.get(0).getTitle(), result.get(0).getTitle());
        assertEquals(books.get(1).getDescription(), result.get(1).getDescription());
    }

    @Test
    public void testBookOperation() {
        // Given
        Long bookId = 1L;
        Book book = new Book(bookId, "Book 1", "Description 1", BigDecimal.valueOf(25.0), "image1.jpg", LocalDateTime.now(), new Category(), new Author(), null);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        // When
        cartService.bookOperation(bookId, CartOperation.INCREASE);

        // Then
        verify(shoppingCart, times(1)).addToCart(any(BookModel.class));
    }
}