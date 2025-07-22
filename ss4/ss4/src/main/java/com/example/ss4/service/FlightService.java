package com.example.ss4.service;

import com.example.ss4.entity.Flight;
import com.example.ss4.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FlightService {

    @Autowired
    private FlightRepository flightRepository;

    // Lấy tất cả chuyến bay với phân trang
    public Page<Flight> getAllFlights(Pageable pageable) {
        return flightRepository.findAll(pageable);
    }

    // Tìm kiếm chuyến bay theo điểm khởi hành và điểm đến
    public Page<Flight> searchFlights(String departure, String destination, Pageable pageable) {
        return flightRepository.findByDepartureAndDestination(departure, destination, pageable);
    }

    // Tìm chuyến bay theo ID
    public Optional<Flight> getFlightById(Long id) {
        return flightRepository.findById(id);
    }

    // Tìm chuyến bay theo số hiệu
    public Flight getFlightByFlightNumber(String flightNumber) {
        return flightRepository.findByFlightNumber(flightNumber);
    }

    // Lưu chuyến bay
    public Flight saveFlight(Flight flight) {
        return flightRepository.save(flight);
    }

    // Xóa chuyến bay
    public void deleteFlight(Long id) {
        flightRepository.deleteById(id);
    }

    // Khởi tạo dữ liệu mẫu
    public void initializeSampleData() {
        if (flightRepository.count() == 0) {
            List<Flight> sampleFlights = List.of(
                    new Flight("VN001", "Hà Nội", "Hồ Chí Minh", new BigDecimal("1500000"),
                            LocalDateTime.of(2024, 12, 1, 8, 0), LocalDateTime.of(2024, 12, 1, 10, 30)),
                    new Flight("VN002", "Hồ Chí Minh", "Hà Nội", new BigDecimal("1600000"),
                            LocalDateTime.of(2024, 12, 1, 9, 0), LocalDateTime.of(2024, 12, 1, 11, 30)),
                    new Flight("VN003", "Hà Nội", "Đà Nẵng", new BigDecimal("1200000"),
                            LocalDateTime.of(2024, 12, 1, 10, 0), LocalDateTime.of(2024, 12, 1, 11, 30)),
                    new Flight("VN004", "Đà Nẵng", "Hà Nội", new BigDecimal("1300000"),
                            LocalDateTime.of(2024, 12, 1, 14, 0), LocalDateTime.of(2024, 12, 1, 15, 30)),
                    new Flight("VN005", "Hồ Chí Minh", "Đà Nẵng", new BigDecimal("1100000"),
                            LocalDateTime.of(2024, 12, 1, 15, 0), LocalDateTime.of(2024, 12, 1, 16, 30)),
                    new Flight("VN006", "Đà Nẵng", "Hồ Chí Minh", new BigDecimal("1000000"),
                            LocalDateTime.of(2024, 12, 1, 16, 0), LocalDateTime.of(2024, 12, 1, 17, 30)),
                    new Flight("VN007", "Hà Nội", "Nha Trang", new BigDecimal("1800000"),
                            LocalDateTime.of(2024, 12, 2, 7, 0), LocalDateTime.of(2024, 12, 2, 9, 30)),
                    new Flight("VN008", "Nha Trang", "Hà Nội", new BigDecimal("1900000"),
                            LocalDateTime.of(2024, 12, 2, 18, 0), LocalDateTime.of(2024, 12, 2, 20, 30)),
                    new Flight("VN009", "Hồ Chí Minh", "Cần Thơ", new BigDecimal("800000"),
                            LocalDateTime.of(2024, 12, 2, 12, 0), LocalDateTime.of(2024, 12, 2, 13, 0)),
                    new Flight("VN010", "Cần Thơ", "Hồ Chí Minh", new BigDecimal("900000"),
                            LocalDateTime.of(2024, 12, 2, 19, 0), LocalDateTime.of(2024, 12, 2, 20, 0))
            );

            flightRepository.saveAll(sampleFlights);
        }
    }
}