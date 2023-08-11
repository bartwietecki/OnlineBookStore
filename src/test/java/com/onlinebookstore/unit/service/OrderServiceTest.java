package com.onlinebookstore.unit.service;

import com.onlinebookstore.entity.Book;
import com.onlinebookstore.entity.Order;
import com.onlinebookstore.entity.OrderStatus;
import com.onlinebookstore.model.BookModel;
import com.onlinebookstore.model.CartBook;
import com.onlinebookstore.model.OrderModel;
import com.onlinebookstore.repository.BookRepository;
import com.onlinebookstore.repository.OrderRepository;
import com.onlinebookstore.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private BookRepository bookRepository;

    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderService = new OrderService(orderRepository, bookRepository);
    }

    @Test
    void testMakeOrder() {
        // Given
        OrderModel orderModel = new OrderModel();
        orderModel.setCustomerFullName("John Smith");
        orderModel.setCustomerEmail("john@example.com");
        orderModel.setCity("New York City");
        orderModel.setZipCode("52-222");
        orderModel.setStreet("5th Avenue");
        orderModel.setStreetNo("5");
        orderModel.setHomeNo("123");

        List<CartBook> cartBooks = new ArrayList<>();
        Book book = new Book();
        book.setId(1L);
        book.setPrice(BigDecimal.valueOf(25.0));

        BookModel bookModel = new BookModel();
        bookModel.setId(book.getId());
        bookModel.setPrice(book.getPrice());

        CartBook cartBook = new CartBook(bookModel);
        cartBooks.add(cartBook);

        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));

        // When
        orderService.makeOrder(orderModel, cartBooks);

        // Then
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void testGetAllOrders() {
        // Given
        Order order1 = new Order(1L, "John Smith", "john@example.com", "City 1", "12345", "Street 1", "A1", "1", BigDecimal.valueOf(50.0), LocalDateTime.now(), OrderStatus.NEW, null);
        Order order2 = new Order(2L, "Billy Kid", "billy@example.com", "City 2", "54321", "Street 2", "A2", "2", BigDecimal.valueOf(100.0), LocalDateTime.now(), OrderStatus.PROCESSING, null);

        List<Order> orders = Arrays.asList(order1, order2);

        when(orderRepository.findAll()).thenReturn(orders);

        // When
        List<OrderModel> result = orderService.getAllOrders();

        // Then
        assertEquals(orders.size(), result.size());
        assertEquals(orders.get(0).getCustomerFullName(), result.get(0).getCustomerFullName());
        assertEquals(orders.get(1).getCustomerEmail(), result.get(1).getCustomerEmail());
    }

    @Test
    void testGetOrderById() {
        // Given
        Long orderId = 1L;
        Order order = new Order();
        order.setId(orderId);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        // When
        OrderModel result = orderService.getOrderById(orderId);

        // Then
        assertEquals(order.getId(), result.getId());
    }

    @Test
    void testUpdateOrder() {
        // Given
        Long orderId = 1L;
        OrderModel updatedOrderModel = new OrderModel();
        updatedOrderModel.setId(orderId);
        updatedOrderModel.setCustomerFullName("Updated Full Name");
        updatedOrderModel.setCustomerEmail("updated@example.com");
        updatedOrderModel.setCity("Updated City");
        updatedOrderModel.setZipCode("12345");
        updatedOrderModel.setStreet("Updated Street");
        updatedOrderModel.setStreetNo("123");
        updatedOrderModel.setHomeNo("4B");
        updatedOrderModel.setPrice(BigDecimal.valueOf(150.0));
        updatedOrderModel.setOrderStatus(OrderStatus.COMPLETED);

        Order existingOrder = new Order();
        existingOrder.setId(orderId);
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(existingOrder));

        // When
        orderService.updateOrder(orderId, updatedOrderModel);

        // Then
        verify(orderRepository, times(1)).save(any(Order.class));
        assertEquals(updatedOrderModel.getCustomerFullName(), existingOrder.getCustomerFullName());
        assertEquals(updatedOrderModel.getCustomerEmail(), existingOrder.getCustomerEmail());
        assertEquals(updatedOrderModel.getCity(), existingOrder.getCity());
        assertEquals(updatedOrderModel.getZipCode(), existingOrder.getZipCode());
        assertEquals(updatedOrderModel.getStreet(), existingOrder.getStreet());
        assertEquals(updatedOrderModel.getStreetNo(), existingOrder.getStreetNo());
        assertEquals(updatedOrderModel.getHomeNo(), existingOrder.getHomeNo());
        assertEquals(updatedOrderModel.getPrice(), existingOrder.getPrice());
        assertEquals(updatedOrderModel.getOrderStatus(), existingOrder.getOrderStatus());
    }

    @Test
    void testDeleteOrder() {
        // Given
        Long orderId = 1L;

        // When
        orderService.deleteOrder(orderId);

        // Then
        verify(orderRepository, times(1)).deleteById(orderId);
    }
}