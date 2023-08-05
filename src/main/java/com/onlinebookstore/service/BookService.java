package com.onlinebookstore.service;

import com.onlinebookstore.entity.Author;
import com.onlinebookstore.entity.Book;
import com.onlinebookstore.entity.Category;
import com.onlinebookstore.model.BookModel;
import com.onlinebookstore.repository.AuthorRepository;
import com.onlinebookstore.repository.BookRepository;
import com.onlinebookstore.repository.CategoryRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    //     metoda potrzebna do paginacji listy książek na stronie
    public Page<BookModel> getAllBooksPaged(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Book> bookPage = bookRepository.findAll(pageable);

        List<BookModel> bookModels = bookPage.getContent().stream()
                .map(this::mapBookToBookModel)
                .collect(Collectors.toList());

        return new PageImpl<>(bookModels, pageable, bookPage.getTotalElements());
    }

    public List<BookModel> getBookModelsByCategory(String categoryName) {
        Category category = categoryRepository.findByName(categoryName);
        List<Book> books = bookRepository.findByCategory(category);
        return books.stream()
                .map(this::mapBookToBookModel)
                .collect(Collectors.toList());
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
        book.setCreateDate(LocalDateTime.now()); // Ustawienie bieżącej daty utworzenia
        book.setCategory(category);
        book.setAuthor(author);

        bookRepository.save(book);
        saveBookImage(file);
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
        bookModel.setCreateDate(book.getCreateDate()); // Przypisz datę utworzenia
        bookModel.setCategoryName(book.getCategory().getName());

        // Ustawienie imienia i nazwiska autora
        Author author = book.getAuthor();
        if (author != null) {
            bookModel.setAuthorName(author.getName());
            bookModel.setAuthorSurname(author.getSurname());
        }

        return bookModel;
    }

    public Page<BookModel> getBooksByKeyword(String keyword, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Book> bookPage = bookRepository.findByKeyword(keyword, pageable);

        List<BookModel> bookModels = bookPage.getContent().stream()
                .map(this::mapBookToBookModel)
                .collect(Collectors.toList());

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
            // here I can handle the situation when the book with the given id was not found
            result.put("book", null);
            result.put("categoryName", "Unknown Category");
            result.put("authorName", "Unknown Author");
        }

        return result;
    }

    private void saveBookImage(MultipartFile file) {
        Path uploads = Paths.get("./uploads");
        try {
            Files.copy(file.getInputStream(), uploads.resolve(file.getOriginalFilename()));
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

}