package com.onlinebookstore.service;

import com.onlinebookstore.entity.Order;
import com.onlinebookstore.model.OrderModel;
import com.onlinebookstore.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    public Order makeOrder(OrderModel orderModel) {
        Order order = new Order();
        order.setCustomerFullName(orderModel.getCustomerFullName());
        order.setCustomerEmail(orderModel.getCustomerEmail());
        order.setCity(orderModel.getCity());
        order.setZipCode(orderModel.getZipCode());
        order.setStreet(orderModel.getStreet());
        order.setStreetNo(orderModel.getStreetNo());
        order.setHomeNo(orderModel.getHomeNo());

        order.setPrice(orderModel.getPrice());

        order.setOrderStatus("NEW");

        return orderRepository.save(order);
    }
}

//
//        List<Book> booksInCart = shoppingCart.getCartBooks().stream()
//                .map(cartBook -> cartBook.getBook())
//                .collect(Collectors.toList());
//        order.setBooks(booksInCart);
//
//        orderRepository.save(order);
//
//    }