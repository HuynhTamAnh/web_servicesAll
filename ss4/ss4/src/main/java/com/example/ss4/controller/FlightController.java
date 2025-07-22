package com.example.ss4.controller;

import com.example.ss4.entity.Flight;
import com.example.ss4.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/flights")
public class FlightController {

    @Autowired
    private FlightService flightService;

    // Hiển thị danh sách chuyến bay
    @GetMapping
    public String listFlights(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection,
            @RequestParam(required = false) String departure,
            @RequestParam(required = false) String destination,
            Model model) {

        // Khởi tạo dữ liệu mẫu nếu chưa có
        flightService.initializeSampleData();

        // Tạo đối tượng Sort
        Sort sort = sortDirection.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        // Tạo đối tượng Pageable
        Pageable pageable = PageRequest.of(page, size, sort);

        // Lấy danh sách chuyến bay
        Page<Flight> flights;
        if ((departure != null && !departure.trim().isEmpty()) ||
                (destination != null && !destination.trim().isEmpty())) {
            flights = flightService.searchFlights(departure, destination, pageable);
        } else {
            flights = flightService.getAllFlights(pageable);
        }

        // Thêm dữ liệu vào model
        model.addAttribute("flights", flights);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", flights.getTotalPages());
        model.addAttribute("totalItems", flights.getTotalElements());
        model.addAttribute("size", size);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("departure", departure);
        model.addAttribute("destination", destination);

        return "flight/list";
    }

    // Hiển thị chi tiết chuyến bay
    @GetMapping("/{id}")
    public String viewFlight(@PathVariable Long id, Model model) {
        Flight flight = flightService.getFlightById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found with ID: " + id));
        model.addAttribute("flight", flight);
        return "flight/detail";
    }

    // Hiển thị form đặt vé
    @GetMapping("/{id}/book")
    public String showBookingForm(@PathVariable Long id, Model model) {
        Flight flight = flightService.getFlightById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found with ID: " + id));
        model.addAttribute("flight", flight);
        return "booking/form";
    }
}