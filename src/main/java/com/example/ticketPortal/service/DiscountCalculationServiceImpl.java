package com.example.ticketPortal.service;

import com.example.ticketPortal.entity.DiscountCard;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class DiscountCalculationServiceImpl implements DiscountCalculationService {
    private static final double BASE_PRICE_PER_KM = 0.05;
    private static final double DISTANCE = 100;
    private static final LocalTime MORNING_PEAK_HOUR_START = LocalTime.of(7, 29);
    private static final LocalTime MORNING_PEAK_HOUR_END = LocalTime.of(9, 30);
    private static final LocalTime AFTERNOON_PEAK_HOUR_START = LocalTime.of(15, 59);
    private static final LocalTime AFTERNOON_PEAK_HOUR_END = LocalTime.of(19, 30);
    public static final double NON_PEAK_HOURS_DISCOUNT = 0.05;
    public static final double PENSIONER_DISCOUNT = 0.34;
    public static final double CHILD_DISCOUNT_WITH_FAMILY_CARD = 0.5;
    public static final double CHILD_DISCOUNT_WITHOUT_FAMILY_CARD = 0.1;

    @Override
    public double calculateTicketPrice(double basePrice, LocalTime departureTime, DiscountCard discountCard, boolean isUnderage) {
        double totalDiscount = 0.0;

        if (isUnderage) {
            if (discountCard == DiscountCard.FAMILY) {
                totalDiscount += CHILD_DISCOUNT_WITH_FAMILY_CARD;
            } else {
                totalDiscount += CHILD_DISCOUNT_WITHOUT_FAMILY_CARD;
            }
        } else if (discountCard == DiscountCard.PENSIONER) {
            totalDiscount += PENSIONER_DISCOUNT;
        }

        if (!isPeakHours(departureTime)) {
            totalDiscount += NON_PEAK_HOURS_DISCOUNT;
        }

        return basePrice - (basePrice * totalDiscount);
    }

    @Override
    public double calculatePriceForTwoWays(double basePrice, boolean isTwoWay) {
        return isTwoWay ? basePrice * 2 : basePrice;
    }

    private boolean isPeakHours(LocalTime time) {
        return (time.isAfter(MORNING_PEAK_HOUR_START) && time.isBefore(MORNING_PEAK_HOUR_END)) ||
                (time.isAfter(AFTERNOON_PEAK_HOUR_START) && time.isBefore(AFTERNOON_PEAK_HOUR_END));
    }
}
