package com.onlinebookstore.service;

import com.onlinebookstore.model.CartBook;
import com.onlinebookstore.entity.Book;
import com.onlinebookstore.entity.Order;
import com.onlinebookstore.model.OrderModel;
import com.onlinebookstore.entity.OrderStatus;
import com.onlinebookstore.repository.BookRepository;
import com.onlinebookstore.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
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

    public List<OrderModel> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(this::mapOrderToOrderModel).collect(Collectors.toList());
    }

    public OrderModel getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order with id " + orderId + " not found"));
        return mapOrderToOrderModel(order);
    }

    public OrderModel updateOrder(Long orderId, OrderModel updatedOrderModel) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order with id " + orderId + " not found"));

        // Update the fields of the order based on the updatedOrderModel
        order.setCustomerFullName(updatedOrderModel.getCustomerFullName());
        order.setCustomerEmail(updatedOrderModel.getCustomerEmail());
        order.setCity(updatedOrderModel.getCity());
        order.setZipCode(updatedOrderModel.getZipCode());
        order.setStreet(updatedOrderModel.getStreet());
        order.setStreetNo(updatedOrderModel.getStreetNo());
        order.setHomeNo(updatedOrderModel.getHomeNo());
        order.setPrice(updatedOrderModel.getPrice());
        order.setOrderStatus(updatedOrderModel.getOrderStatus());

        return mapOrderToOrderModel(orderRepository.save(order));
    }

    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
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
}

