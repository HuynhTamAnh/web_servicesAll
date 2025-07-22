package com.example.ss4.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.example.ss4.entity.Book;
import com.example.ss4.repository.BookRepository;

import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    // Lấy tất cả sách với phân trang và sắp xếp
    public Page<Book> getAllBooks(int page, int size, String sortBy, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return bookRepository.findAll(pageable);
    }

    // Tìm kiếm sách theo title với phân trang
    public Page<Book> searchBooks(String title, int page, int size, String sortBy, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        if (title == null || title.trim().isEmpty()) {
            return bookRepository.findAll(pageable);
        }

        return bookRepository.searchByTitle(title, pageable);
    }

    // Lấy sách theo ID
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    // Lưu sách mới
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    // Cập nhật sách
    public Book updateBook(Long id, Book bookDetails) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            book.setTitle(bookDetails.getTitle());
            book.setAuthor(bookDetails.getAuthor());
            book.setPublisher(bookDetails.getPublisher());
            book.setYear(bookDetails.getYear());
            return bookRepository.save(book);
        }
        return null;
    }

    // Xóa sách
    public boolean deleteBook(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true;
        }
        return false;
    }
}