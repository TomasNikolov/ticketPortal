package com.example.ticketPortal.service;

import com.example.ticketPortal.entity.DiscountCard;

import java.time.LocalTime;

public interface DiscountCalculationService {
    double calculateTicketPrice(double basePrice, LocalTime departureTime, DiscountCard discountCard, boolean isUnderage);
    double calculatePriceForTwoWays(double basePrice, boolean isTwoWay);

}
