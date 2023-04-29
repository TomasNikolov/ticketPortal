package com.example.ticketPortal.controller;

import com.example.ticketPortal.entity.ReservationData;
import com.example.ticketPortal.entity.User;
import com.example.ticketPortal.repository.ReservationRepository;
import com.example.ticketPortal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/adminScreen")
public class AdminController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ReservationRepository trainDataRepository;

    @ModelAttribute("trainDetails")
    public ReservationData trainData() {
        return new ReservationData();
    }

    @GetMapping
    public String displayDashboard(Model model) {
        String user = returnUsername();
        model.addAttribute("userDetails", user);
        return "adminScreen";
    }

    private String returnUsername() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        UserDetails user = (UserDetails) securityContext.getAuthentication().getPrincipal();
        User users = userRepository.findByEmail(user.getUsername());
        return users.getName();
    }

    @PostMapping
    public String saveTrainData(@ModelAttribute("trainDetails") ReservationData trainData, Model model) {
        String user = returnUsername();
        model.addAttribute("userDetails", user);
        trainDataRepository.save(trainData);
        model.addAttribute("trainDetails", new ReservationData());
        return "redirect:/adminScreen?success";
    }
}
