package com.example.ss4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.ss4.entity.Book;
import com.example.ss4.service.BookService;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    // Hiển thị danh sách sách với phân trang và tìm kiếm
    @GetMapping
    public String listBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection,
            @RequestParam(required = false) String search,
            Model model) {

        Page<Book> bookPage;

        if (search != null && !search.trim().isEmpty()) {
            bookPage = bookService.searchBooks(search, page, size, sortBy, sortDirection);
            model.addAttribute("search", search);
        } else {
            bookPage = bookService.getAllBooks(page, size, sortBy, sortDirection);
        }

        model.addAttribute("books", bookPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", bookPage.getTotalPages());
        model.addAttribute("totalItems", bookPage.getTotalElements());
        model.addAttribute("size", size);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDirection", sortDirection);

        return "books/list";
    }

    // Hiển thị form thêm sách mới
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("book", new Book());
        return "books/add";
    }

    // Xử lý thêm sách mới
    @PostMapping("/add")
    public String addBook(@ModelAttribute Book book, RedirectAttributes redirectAttributes) {
        try {
            bookService.saveBook(book);
            redirectAttributes.addFlashAttribute("successMessage", "Thêm sách thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi xảy ra khi thêm sách!");
        }
        return "redirect:/books";
    }

    // Hiển thị form cập nhật sách
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Book book = bookService.getBookById(id).orElse(null);
        if (book == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy sách!");
            return "redirect:/books";
        }
        model.addAttribute("book", book);
        return "books/edit";
    }

    // Xử lý cập nhật sách
    @PostMapping("/edit/{id}")
    public String updateBook(@PathVariable Long id, @ModelAttribute Book book, RedirectAttributes redirectAttributes) {
        try {
            Book updatedBook = bookService.updateBook(id, book);
            if (updatedBook != null) {
                redirectAttributes.addFlashAttribute("successMessage", "Cập nhật sách thành công!");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy sách để cập nhật!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi xảy ra khi cập nhật sách!");
        }
        return "redirect:/books";
    }

    // Xử lý xóa sách
    @PostMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            boolean deleted = bookService.deleteBook(id);
            if (deleted) {
                redirectAttributes.addFlashAttribute("successMessage", "Xóa sách thành công!");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy sách để xóa!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi xảy ra khi xóa sách!");
        }
        return "redirect:/books";
    }

    // Hiển thị chi tiết sách
    @GetMapping("/view/{id}")
    public String viewBook(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Book book = bookService.getBookById(id).orElse(null);
        if (book == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy sách!");
            return "redirect:/books";
        }
        model.addAttribute("book", book);
        return "books/view";
    }
}