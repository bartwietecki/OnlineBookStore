package com.onlinebookstore.unit.service;

import com.onlinebookstore.model.BookModel;
import com.onlinebookstore.service.ShoppingCart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ShoppingCartTest {

    @InjectMocks
    private ShoppingCart shoppingCart;

    @Mock
    private BookModel bookModel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddToCart() {
        // Given
        when(bookModel.getPrice()).thenReturn(new BigDecimal(25));

        // When
        shoppingCart.addToCart(bookModel);

        // Then
        assertEquals(1, shoppingCart.getCartSize());
        assertEquals(new BigDecimal(25), shoppingCart.getTotalCost());
        assertEquals(1, shoppingCart.getCartBooks().size());
        assertEquals(1, shoppingCart.getCartBooks().get(0).getCounter());
        assertEquals(new BigDecimal(25), shoppingCart.getCartBooks().get(0).getPrice());
    }

    @Test
    public void testDecreaseBook() {
        // Given
        when(bookModel.getPrice()).thenReturn(new BigDecimal(25));
        shoppingCart.addToCart(bookModel);

        // When
        shoppingCart.decreaseBook(bookModel);

        // Then
        assertEquals(0, shoppingCart.getCartSize());
        assertEquals(new BigDecimal(0), shoppingCart.getTotalCost());
        assertEquals(0, shoppingCart.getCartBooks().size());
    }

    @Test
    public void testRemoveAllBooks() {
        // Given
        when(bookModel.getPrice()).thenReturn(new BigDecimal(25));
        shoppingCart.addToCart(bookModel);

        // When
        shoppingCart.removeAllBooks(bookModel);

        // Then
        assertEquals(0, shoppingCart.getCartSize());
        assertEquals(new BigDecimal(0), shoppingCart.getTotalCost());
        assertEquals(0, shoppingCart.getCartBooks().size());
    }


    @Test
    public void testGetCartSize() {
        // Given
        when(bookModel.getPrice()).thenReturn(new BigDecimal(25));
        shoppingCart.addToCart(bookModel);

        // When
        int cartSize = shoppingCart.getCartSize();

        // Then
        assertEquals(1, cartSize);
    }

    @Test
    public void testClearShoppingCart() {
        // Given
        when(bookModel.getPrice()).thenReturn(new BigDecimal(25));
        shoppingCart.addToCart(bookModel);

        // When
        shoppingCart.clearShoppingCart();

        // Then
        assertEquals(0, shoppingCart.getCartSize());
        assertEquals(new BigDecimal(0), shoppingCart.getTotalCost());
        assertEquals(0, shoppingCart.getCartBooks().size());
    }
}