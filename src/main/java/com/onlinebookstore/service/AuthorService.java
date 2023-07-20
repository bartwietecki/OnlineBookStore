package com.onlinebookstore.service;

import com.onlinebookstore.entity.Author;
import com.onlinebookstore.model.AuthorModel;
import com.onlinebookstore.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Author addAuthor(AuthorModel authorModel) {
        Author author = new Author();
        author.setName(authorModel.getName());
        author.setSurname(authorModel.getSurname());
        return authorRepository.save(author);
    }

    public Author getAuthorById(Long authorId) {
        return authorRepository.findById(authorId)
                .orElseThrow(() -> new IllegalArgumentException("Author with id " + authorId + " not found"));
    }
}
