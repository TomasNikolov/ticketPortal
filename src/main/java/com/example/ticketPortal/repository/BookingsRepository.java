package com.example.ticketPortal.repository;

import com.example.ticketPortal.entity.Bookings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingsRepository extends JpaRepository<Bookings, Integer> {
    List<Bookings> findByUserId(int userId);
}
