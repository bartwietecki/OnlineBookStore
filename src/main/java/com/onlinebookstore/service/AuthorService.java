package com.onlinebookstore.service;

import com.onlinebookstore.entity.Author;
import com.onlinebookstore.model.AuthorModel;
import com.onlinebookstore.repository.AuthorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<AuthorModel> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        return authors.stream().map(this::mapAuthorToAuthorModel).collect(Collectors.toList());
    }

    public AuthorModel addAuthor(AuthorModel authorModel) {
        Author author = mapAuthorModelToAuthor(authorModel);
        return mapAuthorToAuthorModel(authorRepository.save(author));
    }

    public Optional<AuthorModel> getAuthorById(Long id) {
        return authorRepository.findById(id).map(this::mapAuthorToAuthorModel);
    }

    public AuthorModel updateAuthor(AuthorModel authorModel) {
        Optional<Author> oAuthor = authorRepository.findById(authorModel.getId());
        if (oAuthor.isPresent()) {
            Author author = oAuthor.get();
            author.setName(authorModel.getName());
            author.setSurname(authorModel.getSurname());
            author = authorRepository.save(author);
            return mapAuthorToAuthorModel(author);
        } else {
            throw new EntityNotFoundException("Author with ID " + authorModel.getId() + " not found");
        }
    }

    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }

    private Author mapAuthorModelToAuthor(AuthorModel authorModel) {
        Author author = new Author();
        author.setId(authorModel.getId());
        author.setName(authorModel.getName());
        author.setSurname(authorModel.getSurname());
        return author;
    }

    private AuthorModel mapAuthorToAuthorModel(Author author) {
        AuthorModel authorModel = new AuthorModel();
        authorModel.setId(author.getId());
        authorModel.setName(author.getName());
        authorModel.setSurname(author.getSurname());
        return authorModel;
    }
}
