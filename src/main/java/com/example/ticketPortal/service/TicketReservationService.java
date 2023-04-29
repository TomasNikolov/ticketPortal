package com.example.ticketPortal.service;

import com.example.ticketPortal.entity.Ticket;

import java.util.ArrayList;
import java.util.List;

public class TicketReservationService {
    private List<Ticket> tickets;

    public TicketReservationService() {
        this.tickets = new ArrayList<>();
    }

    public void addTicket(Ticket ticket) {
        this.tickets.add(ticket);
    }

    public boolean reserveTicket(int seatNumber) {
        Ticket ticket = getAvailableTicketBySeatNumber(seatNumber);

        if (ticket == null) {
            System.out.println("Selected seat is already reserved or does not exist!");
            return false;
        }

        ticket.setReserved(true);
        System.out.println("model.Ticket for " + ticket.getDestination() + " has been reserved. Seat number: " + seatNumber);
        return true;
    }

    private Ticket getAvailableTicketBySeatNumber(int seatNumber) {
        for (Ticket ticket : this.tickets) {
            if (ticket.getSeatNumber() == seatNumber && !ticket.isReserved()) {
                return ticket;
            }
        }
        return null;
    }
}