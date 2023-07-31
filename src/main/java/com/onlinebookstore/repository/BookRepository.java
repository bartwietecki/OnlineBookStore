package com.onlinebookstore.repository;

import com.onlinebookstore.entity.Book;
import com.onlinebookstore.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByCategory(Category category);


    // AJAX SEARCH
//    @Query(value="SELECT * FROM Book b WHERE b.title LIKE %:keyword%", nativeQuery = true)
//    List<Book> findByKeyword(@Param("keyword") String keyword);

    @Query("SELECT b FROM Book b WHERE b.title LIKE %:keyword%")
//    List<Book> findByKeyword(@Param("keyword") String keyword);
    Page<Book> findByKeyword(@Param("keyword") String keyword, Pageable pageable);
}

