package com.example.ticketPortal.service;

import com.example.ticketPortal.entity.DiscountCard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

public class DiscountCalculationServiceImplTest {
    private static double BASE_PRICE = 5.00;
    private DiscountCalculationService discountCalculationService;


    @BeforeEach
    public void setUp() {
        discountCalculationService = new DiscountCalculationServiceImpl();
    }

    @Test
    public void calculateTicketPrice_withChildDiscountWithFamilyCardInNonPeakHour() {
        LocalTime departureTime = LocalTime.of(11, 0);
        DiscountCard discountCard = DiscountCard.FAMILY;
        boolean isUnderage = true;

        double expectedPrice = BASE_PRICE - (BASE_PRICE * DiscountCalculationServiceImpl.CHILD_DISCOUNT_WITH_FAMILY_CARD)
                - (BASE_PRICE * DiscountCalculationServiceImpl.NON_PEAK_HOURS_DISCOUNT);
        double actualPrice = discountCalculationService.calculateTicketPrice(BASE_PRICE, departureTime, discountCard, isUnderage);

        Assertions.assertEquals(expectedPrice, actualPrice);
    }

    @Test
    public void calculateTicketPrice_withChildDiscountWithoutFamilyCardInNonPeakHour() {
        LocalTime departureTime = LocalTime.of(10, 40);
        DiscountCard discountCard = DiscountCard.NONE;
        boolean isUnderage = true;

        double expectedPrice = BASE_PRICE - (BASE_PRICE * DiscountCalculationServiceImpl.CHILD_DISCOUNT_WITHOUT_FAMILY_CARD)
                - (BASE_PRICE * DiscountCalculationServiceImpl.NON_PEAK_HOURS_DISCOUNT);
        double actualPrice = discountCalculationService.calculateTicketPrice(BASE_PRICE, departureTime, discountCard, isUnderage);

        Assertions.assertEquals(expectedPrice, actualPrice);
    }

    @Test
    public void calculateTicketPrice_withPensionerDiscountInNonPeakHour() {
        LocalTime departureTime = LocalTime.of(10, 40);
        DiscountCard discountCard = DiscountCard.PENSIONER;
        boolean isUnderage = false;

        double expectedPrice = BASE_PRICE - (BASE_PRICE * DiscountCalculationServiceImpl.PENSIONER_DISCOUNT)
                - (BASE_PRICE * DiscountCalculationServiceImpl.NON_PEAK_HOURS_DISCOUNT);
        double actualPrice = discountCalculationService.calculateTicketPrice(BASE_PRICE, departureTime, discountCard, isUnderage);

        Assertions.assertEquals(expectedPrice, actualPrice);
    }

    @Test
    public void calculateTicketPrice_withPensionerDiscountInPeakHour() {
        LocalTime departureTime = LocalTime.of(7, 40);
        DiscountCard discountCard = DiscountCard.PENSIONER;
        boolean isUnderage = false;

        double expectedPrice = BASE_PRICE - (BASE_PRICE * DiscountCalculationServiceImpl.PENSIONER_DISCOUNT);
        double actualPrice = discountCalculationService.calculateTicketPrice(BASE_PRICE, departureTime, discountCard, isUnderage);

        Assertions.assertEquals(expectedPrice, actualPrice);
    }

    @Test
    public void calculateTicketPrice_withChildDiscountWithFamilyCardInPeakHour() {
        LocalTime departureTime = LocalTime.of(8, 0);
        DiscountCard discountCard = DiscountCard.FAMILY;
        boolean isUnderage = true;

        double expectedPrice = BASE_PRICE - (BASE_PRICE * DiscountCalculationServiceImpl.CHILD_DISCOUNT_WITH_FAMILY_CARD);
        double actualPrice = discountCalculationService.calculateTicketPrice(BASE_PRICE, departureTime, discountCard, isUnderage);

        Assertions.assertEquals(expectedPrice, actualPrice);
    }

    @Test
    public void calculateTicketPrice_withChildDiscountWithoutFamilyCardInPeakHour() {
        LocalTime departureTime = LocalTime.of(8, 0);
        DiscountCard discountCard = DiscountCard.NONE;
        boolean isUnderage = true;

        double expectedPrice = BASE_PRICE - (BASE_PRICE * DiscountCalculationServiceImpl.CHILD_DISCOUNT_WITHOUT_FAMILY_CARD);
        double actualPrice = discountCalculationService.calculateTicketPrice(BASE_PRICE, departureTime, discountCard, isUnderage);

        Assertions.assertEquals(expectedPrice, actualPrice);
    }

    @Test
    public void calculateTicketPrice_withoutChildWithFamilyCardInPeakHour() {
        LocalTime departureTime = LocalTime.of(8, 0);
        DiscountCard discountCard = DiscountCard.FAMILY;
        boolean isUnderage = false;

        double expectedPrice = BASE_PRICE;
        double actualPrice = discountCalculationService.calculateTicketPrice(BASE_PRICE, departureTime, discountCard, isUnderage);

        Assertions.assertEquals(expectedPrice, actualPrice);
    }

    @Test
    public void calculateTicketPrice_withoutChildWithFamilyCardInNonPeakHour() {
        LocalTime departureTime = LocalTime.of(11, 0);
        DiscountCard discountCard = DiscountCard.FAMILY;
        boolean isUnderage = false;

        double expectedPrice = BASE_PRICE - (BASE_PRICE * DiscountCalculationServiceImpl.NON_PEAK_HOURS_DISCOUNT);
        double actualPrice = discountCalculationService.calculateTicketPrice(BASE_PRICE, departureTime, discountCard, isUnderage);

        Assertions.assertEquals(expectedPrice, actualPrice);
    }

    @Test
    public void calculateTicketPrice_withNoPeakHourDiscount() {
        LocalTime departureTime = LocalTime.of(11, 0);
        DiscountCard discountCard = DiscountCard.NONE;
        boolean isUnderage = false;

        double expectedPrice = BASE_PRICE - (BASE_PRICE * DiscountCalculationServiceImpl.NON_PEAK_HOURS_DISCOUNT);
        double actualPrice = discountCalculationService.calculateTicketPrice(BASE_PRICE, departureTime, discountCard, isUnderage);

        Assertions.assertEquals(expectedPrice, actualPrice);
    }

    @Test
    public void calculateTicketPrice_withoutDiscount() {
        LocalTime departureTime = LocalTime.of(7, 30);
        DiscountCard discountCard = DiscountCard.NONE;
        boolean isUnderage = false;

        double expectedPrice = BASE_PRICE;
        double actualPrice = discountCalculationService.calculateTicketPrice(BASE_PRICE, departureTime, discountCard, isUnderage);

        Assertions.assertEquals(expectedPrice, actualPrice);
    }

    @Test
    public void calculatePriceForTwoWays_withIsTwoWayTrue() {
        boolean isTwoWay = true;

        double expectedPrice = BASE_PRICE * 2;
        double actualPrice = discountCalculationService.calculatePriceForTwoWays(BASE_PRICE, isTwoWay);

        Assertions.assertEquals(expectedPrice, actualPrice);
    }

    @Test
    public void calculatePriceForTwoWays_withIsTwoWayFalse() {
        boolean isTwoWay = false;

        double expectedPrice = BASE_PRICE;
        double actualPrice = discountCalculationService.calculatePriceForTwoWays(BASE_PRICE, isTwoWay);

        Assertions.assertEquals(expectedPrice, actualPrice);
    }
}