package com.example.ss4.repository;

import com.example.ss4.entity.Booking;
import com.example.ss4.entity.Booking.BookingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    // Tìm kiếm booking theo số điện thoại khách hàng
    List<Booking> findByCustomerPhone(String customerPhone);

    // Tìm kiếm booking theo tên khách hàng
    List<Booking> findByCustomerName(String customerName);

    // Tìm kiếm booking theo số điện thoại và trạng thái
    List<Booking> findByCustomerPhoneAndStatus(String customerPhone, BookingStatus status);

    // Tìm kiếm booking theo tên khách hàng và trạng thái
    List<Booking> findByCustomerNameAndStatus(String customerName, BookingStatus status);

    // Tìm kiếm booking theo flight ID
    List<Booking> findByFlightId(Long flightId);

    // Tìm kiếm booking theo flight ID và trạng thái
    List<Booking> findByFlightIdAndStatus(Long flightId, BookingStatus status);

    // Tìm kiếm booking với phân trang
    @Query("SELECT b FROM Booking b WHERE " +
            "(:customerName IS NULL OR :customerName = '' OR LOWER(b.customerName) LIKE LOWER(CONCAT('%', :customerName, '%'))) AND " +
            "(:customerPhone IS NULL OR :customerPhone = '' OR b.customerPhone LIKE CONCAT('%', :customerPhone, '%')) AND " +
            "(:status IS NULL OR b.status = :status)")
    Page<Booking> findByCustomerInfo(
            @Param("customerName") String customerName,
            @Param("customerPhone") String customerPhone,
            @Param("status") BookingStatus status,
            Pageable pageable
    );
}