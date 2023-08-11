package com.onlinebookstore.integration.service;

import com.onlinebookstore.entity.Author;
import com.onlinebookstore.entity.Book;
import com.onlinebookstore.entity.Category;
import com.onlinebookstore.model.BookModel;
import com.onlinebookstore.repository.AuthorRepository;
import com.onlinebookstore.repository.CategoryRepository;
import com.onlinebookstore.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class BookServiceIntegrationTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void beforeEachTest() {
        jdbcTemplate.execute("set foreign_key_checks = 0;");
        jdbcTemplate.execute("truncate table book;");
        jdbcTemplate.execute("truncate table category;");
        jdbcTemplate.execute("truncate table author;");
    }

    @Test
    public void testAddBookIntegration_SavesBookCorrectlyInDatabase() {
        // Add test category
        Category category = new Category();
        category.setName("test_category");
        categoryRepository.save(category);

        // Fetch the test category from the database
        Category savedCategory = jdbcTemplate.queryForObject(
                "SELECT id, name FROM category WHERE name = 'test_category'",
                (rs, rowNum) -> new Category(rs.getLong("id"), rs.getString("name"))
        );

        // Add test author
        Author author = new Author();
        author.setName("test_author");
        author.setSurname("Author's Surname");
        authorRepository.save(author);

        // Fetch the test author from the database
        Author savedAuthor = jdbcTemplate.queryForObject(
                "SELECT id, name, surname FROM author WHERE name = 'test_author'",
                (rs, rowNum) -> new Author(rs.getLong("id"), rs.getString("name"), rs.getString("surname"))
        );

        // Given
        BookModel bookModel = new BookModel();
        bookModel.setTitle("Book");
        bookModel.setDescription("Description");
        bookModel.setPrice(BigDecimal.valueOf(50));
        bookModel.setImageName("imageName.jpg");
        bookModel.setCreateDate(LocalDateTime.now());
        bookModel.setCategoryId(savedCategory.getId());
        bookModel.setAuthorId(savedAuthor.getId());

        MultipartFile multipartFile = new MockMultipartFile("imageName.jpg", new byte[0]);

        // When
        bookService.addBook(bookModel, savedCategory.getId(), savedAuthor.getId(), multipartFile);

        // Then
        String sql = "SELECT id, title, description, price, image_name, create_date, category_id, author_id FROM book WHERE title='Book';";
        RowMapper<Book> rowMapper = (rs, rowNum) -> new Book(rs.getLong("id"), rs.getString("title"),
                rs.getString("description"), rs.getBigDecimal("price"), rs.getString("image_name"),
                rs.getTimestamp("create_date").toLocalDateTime(), savedCategory, savedAuthor, null);
        Book bookFromDatabase = jdbcTemplate.queryForObject(sql, rowMapper);

        assertNotNull(bookFromDatabase);
        assertEquals(bookModel.getTitle(), bookFromDatabase.getTitle());
        assertEquals(bookModel.getDescription(), bookFromDatabase.getDescription());
        assertEquals(0, bookModel.getPrice().compareTo(bookFromDatabase.getPrice()));
        assertEquals(bookModel.getImageName(), bookFromDatabase.getImageName());
    }
}