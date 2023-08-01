package com.onlinebookstore.cart;

import com.onlinebookstore.entity.Book;
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

//    public void addToCart(BookModel bookModel) {
//        if (books == null) {
//            books = new ArrayList<>();
//        }
//        books.add(bookModel);
//
//        if (totalCost == null) {
//            totalCost = new BigDecimal(0);
//        }
//        totalCost = totalCost.add(bookModel.getPrice());
//    }
//
//    public int getCartSize() {
//        if (CollectionUtils.isEmpty(books)) {
//            return 0;
//        } else {
//            return books.size();
//        }
//    }
//
//    public void clearShoppingCart() {
//        totalCost = new BigDecimal(0);
//        books.clear();
//        counter = 0;
//    }

    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    public void addBook(Book book) {
        getCartBookByBook(book).ifPresentOrElse(
                CartBook::increaseCounter,
                () -> cartBooks.add(new CartBook(book))
        );
        recalculatePriceAndCounter();
    }


    public void decreaseBook(Book book) {
        Optional<CartBook> oCartBook = getCartBookByBook(book);
        if(oCartBook.isPresent()){
            CartBook cartBook = oCartBook.get();
            cartBook.decreaseCounter();
            if(cartBook.hasZeroBooks()) {
                removeAllIBooks(book);
            }else {
                recalculatePriceAndCounter();
            }
        }
    }


    public void removeAllIBooks(Book book) {
        cartBooks.removeIf(i -> i.idEquals(book));
        recalculatePriceAndCounter();
    }

    private void recalculatePriceAndCounter() {
        totalCost = cartBooks.stream().map(CartBook::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        counter = cartBooks.stream().mapToInt(CartBook::getCounter)
                .reduce(0, Integer::sum);
    }

    private Optional<CartBook> getCartBookByBook(Book book) {
        return cartBooks.stream()
                .filter(i -> i.idEquals(book))
                .findFirst();
    }

}
