package com.example.ss4.repository;

import com.example.ss4.entity.Flight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    // Tìm kiếm chuyến bay theo điểm khởi hành và điểm đến
    @Query("SELECT f FROM Flight f WHERE " +
            "(:departure IS NULL OR :departure = '' OR LOWER(f.departure) LIKE LOWER(CONCAT('%', :departure, '%'))) AND " +
            "(:destination IS NULL OR :destination = '' OR LOWER(f.destination) LIKE LOWER(CONCAT('%', :destination, '%')))")
    Page<Flight> findByDepartureAndDestination(
            @Param("departure") String departure,
            @Param("destination") String destination,
            Pageable pageable
    );

    // Tìm kiếm chuyến bay theo số hiệu
    Flight findByFlightNumber(String flightNumber);

    // Tìm kiếm chuyến bay theo điểm khởi hành
    Page<Flight> findByDepartureContainingIgnoreCase(String departure, Pageable pageable);

    // Tìm kiếm chuyến bay theo điểm đến
    Page<Flight> findByDestinationContainingIgnoreCase(String destination, Pageable pageable);
}