package com.example.ss4.controller;

import com.example.ss4.entity.Booking;
import com.example.ss4.entity.Booking.BookingStatus;
import com.example.ss4.entity.Flight;
import com.example.ss4.service.BookingService;
import com.example.ss4.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private FlightService flightService;

    // Xử lý đặt vé
    @PostMapping
    public String bookFlight(@RequestParam Long flightId,
                             @RequestParam String customerName,
                             @RequestParam String customerPhone,
                             RedirectAttributes redirectAttributes) {
        try {
            Booking booking = bookingService.bookFlight(flightId, customerName, customerPhone);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Đặt vé thành công! Mã đặt vé: " + booking.getId());
            return "redirect:/bookings/" + booking.getId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Đặt vé thất bại: " + e.getMessage());
            return "redirect:/flights/" + flightId + "/book";
        }
    }

    // Hiển thị chi tiết booking
    @GetMapping("/{id}")
    public String viewBooking(@PathVariable Long id, Model model) {
        Booking booking = bookingService.getBookingById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with ID: " + id));
        model.addAttribute("booking", booking);
        return "booking/detail";
    }

    // Hiển thị danh sách booking theo số điện thoại
    @GetMapping
    public String listBookings(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "bookingTime") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection,
            @RequestParam(required = false) String customerName,
            @RequestParam(required = false) String customerPhone,
            @RequestParam(required = false) String status,
            Model model) {

        // Tạo đối tượng Sort
        Sort sort = sortDirection.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        // Tạo đối tượng Pageable
        Pageable pageable = PageRequest.of(page, size, sort);

        // Chuyển đổi status từ String sang enum
        BookingStatus bookingStatus = null;
        if (status != null && !status.trim().isEmpty()) {
            try {
                bookingStatus = BookingStatus.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException e) {
                // Ignore invalid status
            }
        }

        // Lấy danh sách booking
        Page<Booking> bookings = bookingService.searchBookings(customerName, customerPhone,
                bookingStatus, pageable);

        // Thêm dữ liệu vào model
        model.addAttribute("bookings", bookings);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", bookings.getTotalPages());
        model.addAttribute("totalItems", bookings.getTotalElements());
        model.addAttribute("size", size);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("customerName", customerName);
        model.addAttribute("customerPhone", customerPhone);
        model.addAttribute("status", status);
        model.addAttribute("bookingStatuses", BookingStatus.values());

        return "booking/list";
    }

    // Tìm kiếm booking theo số điện thoại
    @GetMapping("/search")
    public String searchBookings(@RequestParam String customerPhone, Model model) {
        List<Booking> bookings = bookingService.getBookingsByCustomerPhone(customerPhone);
        model.addAttribute("bookings", bookings);
        model.addAttribute("customerPhone", customerPhone);
        return "booking/search-results";
    }

    // Hủy booking
    @PostMapping("/{id}/cancel")
    public String cancelBooking(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        boolean success = bookingService.cancelBooking(id);
        if (success) {
            redirectAttributes.addFlashAttribute("successMessage",
                    "Hủy vé thành công!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Không thể hủy vé này. Vé có thể đã được hủy trước đó.");
        }
        return "redirect:/bookings/" + id;
    }
}