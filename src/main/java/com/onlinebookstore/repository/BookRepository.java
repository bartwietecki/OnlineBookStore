package com.onlinebookstore.repository;

import com.onlinebookstore.entity.Book;
import com.onlinebookstore.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
//
//    List<Book> findByCategory(Category category);
}
