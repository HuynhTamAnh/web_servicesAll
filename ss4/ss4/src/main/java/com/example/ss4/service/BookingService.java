package com.example.ss4.service;

import com.example.ss4.entity.Booking;
import com.example.ss4.entity.Booking.BookingStatus;
import com.example.ss4.entity.Flight;
import com.example.ss4.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private FlightService flightService;

    // Đặt vé mới
    public Booking bookFlight(Long flightId, String customerName, String customerPhone) {
        Optional<Flight> flightOpt = flightService.getFlightById(flightId);
        if (flightOpt.isPresent()) {
            Flight flight = flightOpt.get();
            Booking booking = new Booking(flight, customerName, customerPhone,
                    LocalDateTime.now(), BookingStatus.BOOKED);
            return bookingRepository.save(booking);
        }
        throw new RuntimeException("Flight not found with ID: " + flightId);
    }

    // Hủy vé
    public boolean cancelBooking(Long bookingId) {
        Optional<Booking> bookingOpt = bookingRepository.findById(bookingId);
        if (bookingOpt.isPresent()) {
            Booking booking = bookingOpt.get();
            if (booking.getStatus() == BookingStatus.BOOKED) {
                booking.setStatus(BookingStatus.CANCELLED);
                bookingRepository.save(booking);
                return true;
            }
        }
        return false;
    }

    // Lấy tất cả booking của khách hàng theo số điện thoại
    public List<Booking> getBookingsByCustomerPhone(String customerPhone) {
        return bookingRepository.findByCustomerPhone(customerPhone);
    }

    // Lấy booking theo ID
    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }

    // Tìm kiếm booking với phân trang
    public Page<Booking> searchBookings(String customerName, String customerPhone,
                                        BookingStatus status, Pageable pageable) {
        return bookingRepository.findByCustomerInfo(customerName, customerPhone, status, pageable);
    }

    // Lấy tất cả booking với phân trang
    public Page<Booking> getAllBookings(Pageable pageable) {
        return bookingRepository.findAll(pageable);
    }

    // Lấy booking theo trạng thái
    public List<Booking> getBookingsByStatus(BookingStatus status) {
        return bookingRepository.findAll().stream()
                .filter(booking -> booking.getStatus() == status)
                .toList();
    }

    // Lấy booking của một chuyến bay
    public List<Booking> getBookingsByFlightId(Long flightId) {
        return bookingRepository.findByFlightId(flightId);
    }

    // Kiểm tra xem có thể hủy booking không
    public boolean canCancelBooking(Long bookingId) {
        Optional<Booking> bookingOpt = bookingRepository.findById(bookingId);
        if (bookingOpt.isPresent()) {
            Booking booking = bookingOpt.get();
            return booking.getStatus() == BookingStatus.BOOKED;
        }
        return false;
    }
}