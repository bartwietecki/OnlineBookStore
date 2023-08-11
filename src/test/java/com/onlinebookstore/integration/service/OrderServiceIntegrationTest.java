package com.onlinebookstore.integration.service;

import com.onlinebookstore.entity.OrderStatus;
import com.onlinebookstore.model.BookModel;
import com.onlinebookstore.model.CartBook;
import com.onlinebookstore.model.OrderModel;
import com.onlinebookstore.repository.OrderRepository;
import com.onlinebookstore.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class OrderServiceIntegrationTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    public void beforeEachTest() {
        orderRepository.deleteAll();
    }

    @Test
    public void testMakeOrderIntegration_SavesOrderCorrectlyInDatabase() {
        // Given
        OrderModel orderModel = new OrderModel();
        orderModel.setCustomerFullName("John Smith");
        orderModel.setCustomerEmail("john@example.com");
        orderModel.setCity("Sample City");
        orderModel.setZipCode("12345");
        orderModel.setStreet("Sample Street");
        orderModel.setStreetNo("123");
        orderModel.setHomeNo("45");

        List<CartBook> cartBooks = new ArrayList<>();
        BookModel bookModel = new BookModel();
        bookModel.setId(1L);
        bookModel.setTitle("Sample Book");
        bookModel.setDescription("Sample Description");
        bookModel.setPrice(new BigDecimal(25));
        CartBook cartBook = new CartBook(bookModel);
        cartBooks.add(cartBook);

        // When
        orderService.makeOrder(orderModel, cartBooks);

        // Then
        List<OrderModel> orders = orderService.getAllOrders();
        assertEquals(1, orders.size());

        OrderModel savedOrder = orders.get(0);
        assertEquals("John Smith", savedOrder.getCustomerFullName());
        assertEquals("john@example.com", savedOrder.getCustomerEmail());
        assertEquals(0, new BigDecimal(25).compareTo(savedOrder.getPrice()));
        assertEquals(OrderStatus.NEW, savedOrder.getOrderStatus());
    }
}
