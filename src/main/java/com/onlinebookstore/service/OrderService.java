package com.onlinebookstore.service;

import com.onlinebookstore.cart.CartBook;
import com.onlinebookstore.entity.Book;
import com.onlinebookstore.entity.Order;
import com.onlinebookstore.model.OrderModel;
import com.onlinebookstore.order.status.OrderStatus;
import com.onlinebookstore.repository.BookRepository;
import com.onlinebookstore.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;

    public OrderService(OrderRepository orderRepository, BookRepository bookRepository) {
        this.orderRepository = orderRepository;
        this.bookRepository = bookRepository;
    }

//    @Transactional
    public Order makeOrder(OrderModel orderModel, List<CartBook> cartBooks) {
        Order order = new Order();
        order.setCustomerFullName(orderModel.getCustomerFullName());
        order.setCustomerEmail(orderModel.getCustomerEmail());
        order.setCity(orderModel.getCity());
        order.setZipCode(orderModel.getZipCode());
        order.setStreet(orderModel.getStreet());
        order.setStreetNo(orderModel.getStreetNo());
        order.setHomeNo(orderModel.getHomeNo());

        BigDecimal totalPrice = BigDecimal.ZERO;

        List<Book> books = new ArrayList<>();

        for (CartBook cartBook : cartBooks) {
            totalPrice = totalPrice.add(cartBook.getPrice());

            Long bookId = cartBook.getBook().getId();

            Book book = bookRepository.findById(bookId)
                    .orElseThrow(() -> new EntityNotFoundException("Book with id " + bookId + " not found"));

            books.add(book);
        }

        order.setPrice(totalPrice);

        order.setOrderStatus(OrderStatus.NEW);

        order.setBooks(books);

        return orderRepository.save(order);
    }

}


