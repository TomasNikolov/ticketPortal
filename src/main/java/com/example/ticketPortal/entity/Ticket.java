package com.example.ticketPortal.entity;

import java.time.LocalTime;

public class Ticket {
    private String destination;
    private LocalTime departureTime;
    private int seatNumber;
    private boolean isReserved;

    public Ticket(String destination, LocalTime departureTime, int seatNumber, boolean isReserved) {
        this.destination = destination;
        this.departureTime = departureTime;
        this.seatNumber = seatNumber;
        this.isReserved = isReserved;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public boolean isReserved() {
        return isReserved;
    }

    public void setReserved(boolean reserved) {
        isReserved = reserved;
    }
}

