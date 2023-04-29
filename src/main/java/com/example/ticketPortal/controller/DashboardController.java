package com.example.ticketPortal.controller;

import com.example.ticketPortal.DTO.BookingsDTO;
import com.example.ticketPortal.DTO.ReservationDTO;
import com.example.ticketPortal.entity.Bookings;
import com.example.ticketPortal.entity.DiscountCard;
import com.example.ticketPortal.entity.ReservationData;
import com.example.ticketPortal.entity.User;
import com.example.ticketPortal.helper.ObjectCreationHelper;
import com.example.ticketPortal.repository.BookingsRepository;
import com.example.ticketPortal.repository.ReservationRepository;
import com.example.ticketPortal.repository.UserRepository;
import com.example.ticketPortal.service.DefaultUserService;
import com.example.ticketPortal.service.DiscountCalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private DefaultUserService userService;
    private DiscountCalculationService discountCalculationService;

    public DashboardController(DefaultUserService userService, DiscountCalculationService discountCalculationService) {
        super();
        this.userService = userService;
        this.discountCalculationService = discountCalculationService;
    }

    @Autowired
    BookingsRepository bookingsRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ReservationRepository trainDataRepository;

    @ModelAttribute("reservation")
    public ReservationDTO reservationDTO() {
        return new ReservationDTO();
    }

    @GetMapping
    public String displayDashboard(Model model) {
        String user = returnUsername();
        model.addAttribute("userDetails", user);
        return "dashboard";
    }

    @PostMapping
    public String filterTrainData(@ModelAttribute("reservation") ReservationDTO reservationDTO, Model model) {
        List<ReservationData> trainData = trainDataRepository.findByToFromAndDate(reservationDTO.getTo(), reservationDTO.getFrom(), reservationDTO.getFilterDate());


        if (trainData.isEmpty()) {
            trainData = null;
        }
        String user = returnUsername();
        model.addAttribute("userDetails", user);

        model.addAttribute("trainData", trainData);
        model.addAttribute("reservation", reservationDTO);
        return "dashboard";
    }

    @GetMapping("/book/{id}")
    public String bookPage(@PathVariable int id, Model model) {
        Optional<ReservationData> trainData = trainDataRepository.findById(id);
        BookingsDTO bks = ObjectCreationHelper.createBookingsDTO(trainData.get());

        String user = returnUsername();
        model.addAttribute("userDetails", user);

        model.addAttribute("record", bks);
        return "books";
    }

    @PostMapping("/booking")
    public String finalBooking(@ModelAttribute("record") BookingsDTO bookingDTO, Model model) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        UserDetails user = (UserDetails) securityContext.getAuthentication().getPrincipal();
        Bookings b = userService.updateBookings(bookingDTO, user);
        model.addAttribute("record", new BookingsDTO());
        return "redirect:/myBooking";
    }

    @PostMapping("/calculate_price")
    public String calculatePrice(@ModelAttribute("record") BookingsDTO bookingDTO, Model model) {
        String user = returnUsername();

        bookingDTO.setPrice(discountCalculationService.calculateTicketPrice(bookingDTO.getPrice(), LocalTime.parse(bookingDTO.getTime()), bookingDTO.getDiscountCard(), bookingDTO.getIsUnderage()));

        model.addAttribute("userDetails", user);
        model.addAttribute("record", bookingDTO);

        return "books_final";
    }

    private String returnUsername() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        UserDetails user = (UserDetails) securityContext.getAuthentication().getPrincipal();
        User users = userRepository.findByEmail(user.getUsername());
        return users.getName();
    }
}