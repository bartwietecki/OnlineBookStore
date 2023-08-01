package com.onlinebookstore.cart;

import com.onlinebookstore.entity.Book;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartBook {

    private Book book;
    private int counter;
    private BigDecimal price;

    public CartBook(Book book) {
        this.book = book;
        this.counter = 1;
        this.price = book.getPrice();
    }

    public void increaseCounter() {
        counter++;
        recalculate();
    }

    public void decreaseCounter() {
        if (counter > 0 ) {
            counter--;
            recalculate();
        }
    }

    public boolean hasZeroBooks() {
        return counter == 0;
    }

    private void recalculate() {
        price = book.getPrice().multiply(new BigDecimal(counter));
    }

    public boolean idEquals(Book book) {
        return this.book.getId().equals(book.getId());
    }

}
