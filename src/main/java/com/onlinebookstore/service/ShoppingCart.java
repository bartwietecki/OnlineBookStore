package com.onlinebookstore.service;

import com.onlinebookstore.model.CartBook;
import com.onlinebookstore.model.BookModel;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Data
public class ShoppingCart {

    private List<CartBook> cartBooks;
    private int counter = 0;
    private BigDecimal totalCost;

    public void addToCart(BookModel book) {
        if (cartBooks == null) {
            cartBooks = new ArrayList<>();
        }

        getCartBookByBook(book).ifPresentOrElse(
                CartBook::increaseCounter,
                () -> {
                    CartBook cartBook = new CartBook(book);
                    cartBooks.add(cartBook);
                });

        if (totalCost == null) {
            totalCost = new BigDecimal(0);
        }
        totalCost = totalCost.add(book.getPrice());

        recalculatePriceAndCounter();
    }

    public void decreaseBook(BookModel book) {
        Optional<CartBook> oCartBook = getCartBookByBook(book);
        if (oCartBook.isPresent()) {
            CartBook cartBook = oCartBook.get();
            cartBook.decreaseCounter();
            if (cartBook.hasZeroBooks()) {
                removeAllBooks(book);
            } else {
                recalculatePriceAndCounter();
            }
        }
    }

    public void removeAllBooks(BookModel book) {
        cartBooks.removeIf(i -> i.idEquals(book));
        recalculatePriceAndCounter();
    }

    private void recalculatePriceAndCounter() {
        totalCost = cartBooks.stream().map(CartBook::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        counter = cartBooks.stream().mapToInt(CartBook::getCounter)
                .reduce(0, Integer::sum);
    }

    private Optional<CartBook> getCartBookByBook(BookModel book) {
        return cartBooks.stream()
                .filter(i -> i.idEquals(book))
                .findFirst();
    }

    public int getCartSize() {
        if (CollectionUtils.isEmpty(cartBooks)) {
            return 0;
        } else {
            return cartBooks.size();
        }
    }

    public void clearShoppingCart() {
        totalCost = new BigDecimal(0);
        cartBooks.clear();
    }
}