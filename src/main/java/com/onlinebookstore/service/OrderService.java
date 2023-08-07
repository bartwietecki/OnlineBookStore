package com.onlinebookstore.service;

import com.onlinebookstore.model.CartBook;
import com.onlinebookstore.entity.Book;
import com.onlinebookstore.entity.Order;
import com.onlinebookstore.model.OrderModel;
import com.onlinebookstore.entity.OrderStatus;
import com.onlinebookstore.repository.BookRepository;
import com.onlinebookstore.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;

    public OrderService(OrderRepository orderRepository, BookRepository bookRepository) {
        this.orderRepository = orderRepository;
        this.bookRepository = bookRepository;
    }

    @Transactional
    public void makeOrder(OrderModel orderModel, List<CartBook> cartBooks) {

        if (orderModel.getCustomerFullName() == null || orderModel.getCustomerFullName().trim().isEmpty()
                || orderModel.getCustomerEmail() == null || orderModel.getCustomerEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Customer full name and email cannot be blank");
        }

        Order order = new Order();
        copyOrderDataForMakeOrder(order, orderModel);

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

        orderRepository.save(order);
    }

    public List<OrderModel> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(this::mapOrderToOrderModel)
                .collect(Collectors.toList());
    }

    public OrderModel getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order with id " + orderId + " not found"));
        return mapOrderToOrderModel(order);
    }

    public void updateOrder(Long orderId, OrderModel orderModel) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order with id " + orderId + " not found"));

        copyOrderDataForMakeOrder(order, orderModel);
        copyOrderDataForOtherMethods(order, orderModel);

        orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    private OrderModel mapOrderToOrderModel(Order order) {
        OrderModel orderModel = new OrderModel();
        orderModel.setId(order.getId());
        orderModel.setCustomerFullName(order.getCustomerFullName());
        orderModel.setCustomerEmail(order.getCustomerEmail());
        orderModel.setCity(order.getCity());
        orderModel.setZipCode(order.getZipCode());
        orderModel.setStreet(order.getStreet());
        orderModel.setStreetNo(order.getStreetNo());
        orderModel.setHomeNo(order.getHomeNo());
        orderModel.setPrice(order.getPrice());
        orderModel.setOrderStatus(order.getOrderStatus());

        return orderModel;
    }

    private void copyOrderDataForMakeOrder(Order order, OrderModel orderModel) {
        order.setCustomerFullName(orderModel.getCustomerFullName());
        order.setCustomerEmail(orderModel.getCustomerEmail());
        order.setCity(orderModel.getCity());
        order.setZipCode(orderModel.getZipCode());
        order.setStreet(orderModel.getStreet());
        order.setStreetNo(orderModel.getStreetNo());
        order.setHomeNo(orderModel.getHomeNo());
    }

    private void copyOrderDataForOtherMethods(Order order, OrderModel orderModel) {
        order.setPrice(orderModel.getPrice());
        order.setOrderStatus(orderModel.getOrderStatus());
    }
}