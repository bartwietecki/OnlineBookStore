package com.onlinebookstore.unit.service;

import com.onlinebookstore.entity.Author;
import com.onlinebookstore.entity.Book;
import com.onlinebookstore.entity.Category;
import com.onlinebookstore.model.BookModel;
import com.onlinebookstore.repository.AuthorRepository;
import com.onlinebookstore.repository.BookRepository;
import com.onlinebookstore.repository.CategoryRepository;
import com.onlinebookstore.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private AuthorRepository authorRepository;

    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bookService = new BookService(bookRepository, categoryRepository, authorRepository);
    }

    @Test
    void testGetAllBooksPaged() {
        // Given
        int pageNumber = 0;
        int pageSize = 9;

        Book book1 = new Book(1L, "Book 1", "Description 1", BigDecimal.valueOf(25), "image1.jpg", LocalDateTime.now(), new Category(), new Author(), null);
        Book book2 = new Book(2L, "Book 2", "Description 2", BigDecimal.valueOf(30), "image2.jpg", LocalDateTime.now(), new Category(), new Author(), null);

        List<Book> bookList = Arrays.asList(book1, book2);

        Page<Book> bookPage = new PageImpl<>(bookList);

        when(bookRepository.findAll(any(Pageable.class))).thenReturn(bookPage);

        // When
        Page<BookModel> resultPage = bookService.getAllBooksPaged(pageNumber, pageSize);

        // Then
        assertEquals(bookList.size(), resultPage.getContent().size());
        assertEquals(bookList.get(0).getTitle(), resultPage.getContent().get(0).getTitle());
        assertEquals(bookList.get(1).getDescription(), resultPage.getContent().get(1).getDescription());
    }

    @Test
    void testGetAllBooks() {
        // Given
        Book book1 = new Book(1L, "Book 1", "Description 1", BigDecimal.valueOf(25), "image1.jpg", LocalDateTime.now(), new Category(), new Author(), null);
        Book book2 = new Book(2L, "Book 2", "Description 2", BigDecimal.valueOf(30), "image2.jpg", LocalDateTime.now(), new Category(), new Author(), null);

        List<Book> bookList = Arrays.asList(book1, book2);

        when(bookRepository.findAll()).thenReturn(bookList);

        // When
        List<BookModel> result = bookService.getAllBooks();

        // Then
        assertEquals(bookList.size(), result.size());
        assertEquals(bookList.get(0).getTitle(), result.get(0).getTitle());
        assertEquals(bookList.get(1).getDescription(), result.get(1).getDescription());
    }

    @Test
    void testAddBook() {
        // Given
        BookModel bookModel = new BookModel();
        bookModel.setTitle("Book Title");
        bookModel.setDescription("Book Description");
        bookModel.setPrice(new BigDecimal("29.99"));
        bookModel.setCategoryId(1L);
        bookModel.setAuthorId(1L);
        MultipartFile imageFile = new MockMultipartFile("image.jpg", new byte[0]);

        Category category = new Category(1L, "Category");
        Author author = new Author(1L, "John", "Smith");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

        // When
        bookService.addBook(bookModel, 1L, 1L, imageFile);

        // Then
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void testGetBooksByKeyword() {
        // Given
        String keyword = "book";
        Pageable pageable = PageRequest.of(0, 9);
        Page<Book> bookPage = new PageImpl<>(Collections.emptyList());

        when(bookRepository.findByKeyword(keyword, pageable)).thenReturn(bookPage);

        // When
        Page<BookModel> result = bookService.getBooksByKeyword(keyword, 0, 9);

        // Then
        assertTrue(result.getContent().isEmpty());
    }

    @Test
    void testGetBookById() {
        // Given
        Long bookId = 1L;
        Book book = new Book(bookId, "Book Title", "Book Description", new BigDecimal("29.99"), "image.jpg", LocalDateTime.now(), new Category(), new Author(), null);
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        // When
        Map<String, Object> result = bookService.getBookById(bookId);

        // Then
        assertNotNull(result.get("book"));
        assertEquals("Book Title", ((BookModel) result.get("book")).getTitle());
    }

    @Test
    void testUpdateBook() {
        // Given
        Long bookId = 1L;
        BookModel bookModel = new BookModel();
        bookModel.setId(bookId);
        bookModel.setTitle("Updated Book Title");
        bookModel.setDescription("Updated Book Description");
        bookModel.setPrice(new BigDecimal("39.99"));
        bookModel.setCategoryId(2L);
        bookModel.setAuthorId(2L);

        Book existingBook = new Book(1L, "Book 1", "Description 1", BigDecimal.valueOf(25), "image1.jpg", LocalDateTime.now(), new Category(), new Author(), null);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(existingBook));

        Category updatedCategory = new Category(2L, "Updated Category");
        Author updatedAuthor = new Author(2L, "Updated John", "Updated Smith");
        when(categoryRepository.findById(2L)).thenReturn(Optional.of(updatedCategory));
        when(authorRepository.findById(2L)).thenReturn(Optional.of(updatedAuthor));

        // When
        bookService.updateBook(bookModel, 2L, 2L);

        // Then
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void testDeleteBook() {
        // Given
        Long bookId = 1L;

        // When
        bookService.deleteBook(bookId);

        // Then
        verify(bookRepository, times(1)).deleteById(bookId);
    }
}