package com.onlinebookstore.integration.service;

import com.onlinebookstore.entity.Author;
import com.onlinebookstore.model.AuthorModel;
import com.onlinebookstore.repository.AuthorRepository;
import com.onlinebookstore.service.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AuthorServiceIntegrationTest {

    @Autowired
    private AuthorService authorService;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void beforeEachTest() {
        jdbcTemplate.execute("set foreign_key_checks = 0;");
        jdbcTemplate.execute("truncate table author;");
    }

    @Test
    public void testAddAuthorIntegration_SavesAuthorCorrectlyInDatabase() {
        // Given
        AuthorModel authorModel = new AuthorModel();
        authorModel.setName("Billy");
        authorModel.setSurname("Kid");

        // When
        authorService.addAuthor(authorModel);

        // Then
        List<Author> authors = authorRepository.findAll();
        assertEquals(1, authors.size());

        Author savedAuthor = authors.get(0);
        assertEquals("Billy", savedAuthor.getName());
        assertEquals("Kid", savedAuthor.getSurname());
    }

    @Test
    public void testGetAllAuthorsIntegration_ReturnsAllAuthors() {
        // Given
        AuthorModel authorModel1 = new AuthorModel();
        authorModel1.setName("John");
        authorModel1.setSurname("Smith");

        AuthorModel authorModel2 = new AuthorModel();
        authorModel2.setName("Billy");
        authorModel2.setSurname("Kid");

        authorService.addAuthor(authorModel1);
        authorService.addAuthor(authorModel2);

        // When
        List<AuthorModel> allAuthors = authorService.getAllAuthors();

        // Then
        assertEquals(2, allAuthors.size());

        AuthorModel firstAuthor = allAuthors.get(0);
        assertEquals("John", firstAuthor.getName());
        assertEquals("Smith", firstAuthor.getSurname());

        AuthorModel secondAuthor = allAuthors.get(1);
        assertEquals("Billy", secondAuthor.getName());
        assertEquals("Kid", secondAuthor.getSurname());
    }

    @Test
    public void testUpdateAuthorIntegration_UpdatesAuthorCorrectly() {
        // Given
        AuthorModel authorModel = new AuthorModel();
        authorModel.setName("John");
        authorModel.setSurname("Smith");

        authorService.addAuthor(authorModel);

        List<Author> authors = authorRepository.findAll();
        assertEquals(1, authors.size());

        Author savedAuthor = authors.get(0);

        // When
        savedAuthor.setName("UpdatedName");
        savedAuthor.setSurname("UpdatedSurname");

        AuthorModel updatedAuthorModel = new AuthorModel();
        updatedAuthorModel.setId(savedAuthor.getId());
        updatedAuthorModel.setName("UpdatedName");
        updatedAuthorModel.setSurname("UpdatedSurname");
        authorService.updateAuthor(updatedAuthorModel);


        // Then
        List<Author> updatedAuthors = authorRepository.findAll();
        assertEquals(1, updatedAuthors.size());

        Author updatedAuthor = updatedAuthors.get(0);
        assertEquals("UpdatedName", updatedAuthor.getName());
        assertEquals("UpdatedSurname", updatedAuthor.getSurname());
    }

    @Test
    public void testDeleteAuthorIntegration_DeletesAuthorCorrectly() {
        // Given
        AuthorModel authorModel = new AuthorModel();
        authorModel.setName("John");
        authorModel.setSurname("Smith");
        authorService.addAuthor(authorModel);

        List<Author> authors = authorRepository.findAll();
        assertEquals(1, authors.size());

        Author savedAuthor = authors.get(0);

        // When
        authorService.deleteAuthor(savedAuthor.getId());

        // Then
        List<Author> remainingAuthors = authorRepository.findAll();
        assertEquals(0, remainingAuthors.size());
    }
}