package com.example.ticketPortal.service;

import com.example.ticketPortal.entity.Ticket;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

public class TicketReservationServiceTest {

    private TicketReservationService ticketReservationService;

    @BeforeEach
    public void setUp() {
        ticketReservationService = new TicketReservationService();
        ticketReservationService.addTicket(new Ticket( "Destination 1", LocalTime.of(11, 0), 5, false));
        ticketReservationService.addTicket(new Ticket( "Destination 2", LocalTime.of(8, 0), 4, true));
    }

    @Test
    public void testReserveTicket_success() {
        Boolean expectedResult = true;
        Boolean actualResult = ticketReservationService.reserveTicket(5);

        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testReserveTicket_seatAlreadyReserved() {
        Boolean expectedResult = false;
        Boolean actualResult = ticketReservationService.reserveTicket(4);

        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testReserveTicket_seatDoesNotExist() {
        Boolean expectedResult = false;
        Boolean actualResult = ticketReservationService.reserveTicket(10);

        Assertions.assertEquals(expectedResult, actualResult);
    }
}