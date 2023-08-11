package com.onlinebookstore.integration.controller;

import com.onlinebookstore.entity.CartOperation;
import com.onlinebookstore.service.CartService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CartControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    @Test
    public void testAddBookToCart_RedirectsToBooksAfterAddingBook() throws Exception {
        mockMvc.perform(post("/cart/save")
                        .param("bookId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books"));

        verify(cartService, times(1)).bookOperation(1L, CartOperation.INCREASE);
    }

    @Test
    public void testIncreaseBook_RedirectsToCartAfterIncreasingBook() throws Exception {
        mockMvc.perform(get("/cart/increase/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cart"));

        verify(cartService, times(1)).bookOperation(1L, CartOperation.INCREASE);
    }

    @Test
    public void testDecreaseBook_RedirectsToCartAfterDecreasingBook() throws Exception {
        mockMvc.perform(get("/cart/decrease/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cart"));

        verify(cartService, times(1)).bookOperation(1L, CartOperation.DECREASE);
    }

    @Test
    public void testRemoveBooksFromCart_RedirectsToCartAfterRemovingBook() throws Exception {
        mockMvc.perform(get("/cart/remove/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cart"));

        verify(cartService, times(1)).bookOperation(1L, CartOperation.REMOVE);
    }
}