package com.example.ticketPortal.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.example.ticketPortal.DTO.BookingsDTO;
import com.example.ticketPortal.entity.Bookings;
import com.example.ticketPortal.entity.User;
import com.example.ticketPortal.helper.ObjectCreationHelper;
import com.example.ticketPortal.repository.BookingsRepository;
import com.example.ticketPortal.repository.UserRepository;
import com.example.ticketPortal.service.DefaultUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/myBooking")
public class MyBookingController {

    private DefaultUserService userService;

    public MyBookingController(DefaultUserService userService) {
        super();
        this.userService = userService;
    }

    @Autowired
    BookingsRepository bookingsRepository;

    @Autowired
    UserRepository userRepository;

    @ModelAttribute("bookings")
    public BookingsDTO bookingDto() {
        return new BookingsDTO();
    }

    @GetMapping
    public String login(Model model) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        UserDetails users = (UserDetails) securityContext.getAuthentication().getPrincipal();
        User user = userRepository.findByEmail(users.getUsername());
        List<BookingsDTO> bks = new ArrayList<>();
        List<Bookings> bs = bookingsRepository.findByUserId(user.getId());
        for (Bookings bookings : bs) {
            BookingsDTO bkks = ObjectCreationHelper.createBookingsDTO(bookings);
            bks.add(bkks);
        }
        model.addAttribute("userDetails", user.getName());
        Collections.reverse(bks);
        model.addAttribute("bookings", bks);
        return "myBookings";
    }

    @GetMapping("/generate/{id}")
    public String bookPage(@PathVariable int id, Model model) {
        Optional<Bookings> trainData = bookingsRepository.findById(id);
        Optional<User> users = userRepository.findById(trainData.get().getUserId());
        String user = users.get().getName();
        BookingsDTO bks = ObjectCreationHelper.createBookingsDTO(trainData.get());
        userService.sendEmail(bks, users.get(), trainData.get().getFileName());
        model.addAttribute("userDetails", user);
        List<Bookings> bs = bookingsRepository.findByUserId(users.get().getId());
        Collections.reverse(bs);
        model.addAttribute("bookings", bs);
        return "redirect:/myBooking?success";
    }

    @GetMapping("/cancel/{id}")
    public String cancelBooking(@PathVariable int id, Model model) {
        Optional<Bookings> trainData = bookingsRepository.findById(id);
        if (!trainData.get().isTripStatus()) {
            setData(trainData.get(), model);
            return "redirect:/myBooking?alreadyCancel";
        } else {
            setData(trainData.get(), model);
            trainData.get().setTripStatus(false);
            bookingsRepository.save(trainData.get());

            return "redirect:/myBooking?successCancel";

        }
    }

    private void setData(Bookings trainData, Model model) {
        Optional<User> users = userRepository.findById(trainData.getUserId());
        List<Bookings> bs = bookingsRepository.findByUserId(users.get().getId());
        Collections.reverse(bs);
        model.addAttribute("bookings", bs);
    }
}