package com.onlinebookstore.unit.service;

import com.onlinebookstore.entity.Author;
import com.onlinebookstore.model.AuthorModel;
import com.onlinebookstore.repository.AuthorRepository;
import com.onlinebookstore.service.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    private AuthorService authorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authorService = new AuthorService(authorRepository);
    }

    @Test
    void testGetAllAuthors() {
        // Given
        Author author1 = new Author(1L, "John", "Smith");
        Author author2 = new Author(2L, "Billy", "Kid");

        List<Author> authorList = Arrays.asList(author1, author2);

        when(authorRepository.findAll()).thenReturn(authorList);

        // When
        List<AuthorModel> result = authorService.getAllAuthors();

        // Then
        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getName());
        assertEquals("Smith", result.get(0).getSurname());
        assertEquals("Billy", result.get(1).getName());
        assertEquals("Kid", result.get(1).getSurname());
    }

    @Test
    void testAddAuthor() {
        // Given
        AuthorModel authorModel = new AuthorModel();
        authorModel.setName("John");
        authorModel.setSurname("Smith");

        // When
        authorService.addAuthor(authorModel);

        // Then
        verify(authorRepository, times(1)).save(any(Author.class));
    }

    @Test
    void testGetAuthorById() {
        // Given
        Long authorId = 1L;
        Author author = new Author(authorId, "John", "Smith");
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));

        // When
        Optional<AuthorModel> result = authorService.getAuthorById(authorId);

        // Then
        assertTrue(result.isPresent());
        assertEquals(author.getName(), result.get().getName());
        assertEquals(author.getSurname(), result.get().getSurname());
    }

    @Test
    void testUpdateAuthor() {
        // Given
        Long authorId = 1L;
        AuthorModel authorModel = new AuthorModel();
        authorModel.setId(authorId);
        authorModel.setName("John");
        authorModel.setSurname("Smith");

        Author existingAuthor = new Author(authorId, "John", "Kid");
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(existingAuthor));

        // When
        authorService.updateAuthor(authorModel);

        // Then
        verify(authorRepository, times(1)).save(any(Author.class));
        assertEquals(authorModel.getName(), existingAuthor.getName());
        assertEquals(authorModel.getSurname(), existingAuthor.getSurname());
    }

    @Test
    void testDeleteAuthor() {
        // Given
        Long authorId = 1L;

        // When
        authorService.deleteAuthor(authorId);

        // Then
        verify(authorRepository, times(1)).deleteById(authorId);
    }
}