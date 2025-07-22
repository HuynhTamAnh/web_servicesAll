package com.example.ss4.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.ss4.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // Tìm kiếm sách theo title với phân trang
    @Query("SELECT b FROM Book b WHERE b.title LIKE %:title%")
    Page<Book> findByTitleContainingIgnoreCase(@Param("title") String title, Pageable pageable);

    // Tìm kiếm sách theo title (không phân trang)
    @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    Page<Book> searchByTitle(@Param("title") String title, Pageable pageable);
}