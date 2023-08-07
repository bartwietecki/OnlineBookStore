package com.onlinebookstore.service;

import com.onlinebookstore.entity.Author;
import com.onlinebookstore.entity.Book;
import com.onlinebookstore.entity.Category;
import com.onlinebookstore.model.BookModel;
import com.onlinebookstore.repository.AuthorRepository;
import com.onlinebookstore.repository.BookRepository;
import com.onlinebookstore.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository, CategoryRepository categoryRepository,
                       AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
        this.authorRepository = authorRepository;
    }

    public Page<BookModel> getAllBooksPaged(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Book> bookPage = bookRepository.findAll(pageable);

        List<BookModel> bookModels = bookPage.getContent().stream()
                .map(this::mapBookToBookModel)
                .collect(Collectors.toList());

        return new PageImpl<>(bookModels, pageable, bookPage.getTotalElements());
    }

    public List<BookModel> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream().map(this::mapBookToBookModel).collect(Collectors.toList());
    }

    public void addBook(BookModel bookModel, Long categoryId, Long authorId, MultipartFile file) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category with id " + categoryId + " not found"));

        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new IllegalArgumentException("Author with id " + authorId + " not found"));

        Book book = new Book();
        book.setTitle(bookModel.getTitle());
        book.setDescription(bookModel.getDescription());
        book.setPrice(bookModel.getPrice());
        book.setImageName(bookModel.getImageName());
        book.setCreateDate(LocalDateTime.now());
        book.setCategory(category);
        book.setAuthor(author);

        bookRepository.save(book);
        saveBookImage(file);
    }

    public Page<BookModel> getBooksByKeyword(String keyword, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Book> bookPage = bookRepository.findByKeyword(keyword, pageable);

        List<BookModel> bookModels = bookPage.getContent().stream()
                .map(this::mapBookToBookModel)
                .collect(Collectors.toList());

//        if (bookModels.isEmpty()) {
//            throw new EntityNotFoundException("No books found for the keyword: " + keyword);
//        }

        return new PageImpl<>(bookModels, pageable, bookPage.getTotalElements());
    }

    public Map<String, Object> getBookById(Long bookId) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        Map<String, Object> result = new HashMap<>();

        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            BookModel bookModel = mapBookToBookModel(book);
            result.put("book", bookModel);

            String categoryName = categoryRepository.findById(book.getCategory().getId())
                    .map(Category::getName)
                    .orElse("Unknown Category");
            result.put("categoryName", categoryName);

            String authorName = authorRepository.findById(book.getAuthor().getId())
                    .map(Author::getName)
                    .orElse("Unknown Author");
            result.put("authorName", authorName);
        } else {
            throw new EntityNotFoundException("Book with ID " + bookId + " not found");
        }

        return result;
    }

    public void updateBook(BookModel bookModel, Long categoryId, Long authorId) {
        Optional<Book> oBook = bookRepository.findById(bookModel.getId());
        if (oBook.isPresent()) {
            Book book = oBook.get();
            book.setTitle(bookModel.getTitle());
            book.setDescription(bookModel.getDescription());
            book.setPrice(bookModel.getPrice());
            book.setImageName(bookModel.getImageName());
            book.setCreateDate(bookModel.getCreateDate());

            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new IllegalArgumentException("Category with id " + bookModel.getCategoryId() + " not found"));
            book.setCategory(category);

            Author author = authorRepository.findById(authorId)
                    .orElseThrow(() -> new IllegalArgumentException("Author with id " + bookModel.getAuthorId() + " not found"));
            book.setAuthor(author);

            bookRepository.save(book);
        } else {
            throw new EntityNotFoundException("Book with ID " + bookModel.getId() + " not found");
        }
    }

    public void deleteBook(Long bookId) {
        bookRepository.deleteById(bookId);
    }

    // TODO
    // Finish saveBookImage() method
    private void saveBookImage(MultipartFile file) {
        Path uploads = Paths.get("./uploads");
        try {
            Files.copy(file.getInputStream(), uploads.resolve(file.getOriginalFilename()));
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private BookModel mapBookToBookModel(Book book) {
        BookModel bookModel = new BookModel();
        bookModel.setId(book.getId());
        bookModel.setTitle(book.getTitle());
        bookModel.setDescription(book.getDescription());
        bookModel.setPrice(book.getPrice());
        bookModel.setImageName(book.getImageName());
        bookModel.setCategoryId(book.getCategory().getId());
        bookModel.setAuthorId(book.getAuthor().getId());
        bookModel.setCreateDate(book.getCreateDate());
        bookModel.setCategoryName(book.getCategory().getName());

        Category category = book.getCategory();
        if (category != null) {
            bookModel.setCategoryName(category.getName());
        }

        Author author = book.getAuthor();
        if (author != null) {
            bookModel.setAuthorName(author.getName());
            bookModel.setAuthorSurname(author.getSurname());
        }

        return bookModel;
    }
}