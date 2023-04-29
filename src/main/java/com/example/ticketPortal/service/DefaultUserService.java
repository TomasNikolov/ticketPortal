package com.example.ticketPortal.service;

import com.example.ticketPortal.entity.User;
import com.example.ticketPortal.entity.Bookings;
import com.example.ticketPortal.DTO.BookingsDTO;
import com.example.ticketPortal.DTO.UserRegisteredDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface DefaultUserService extends UserDetailsService {
    User save(UserRegisteredDTO userRegisteredDTO);

    Bookings updateBookings(BookingsDTO bookingDTO, UserDetails user);

    void sendEmail(BookingsDTO bookingDTO, User users, String nameGenerator);
}

