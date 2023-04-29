package com.example.ticketPortal.helper;

import com.example.ticketPortal.DTO.BookingsDTO;
import com.example.ticketPortal.entity.Bookings;
import com.example.ticketPortal.entity.ReservationData;

public class ObjectCreationHelper {

    public static BookingsDTO createBookingsDTO(ReservationData reservationData) {
        BookingsDTO bks = new BookingsDTO();

        bks.setTrainName(reservationData.getTrainName());
        bks.setFilterDate(reservationData.getFilterDate());
        bks.setFromDestination(reservationData.getFromDestination());
        bks.setToDestination(reservationData.getToDestination());
        bks.setPrice(reservationData.getPrice());
        bks.setTime(reservationData.getTime());
        bks.setTotalCalculated(0.0);

        return bks;
    }

    public static BookingsDTO createBookingsDTO(Bookings trainData) {
        BookingsDTO bks = new BookingsDTO();
        bks.setId(trainData.getId());

        bks.setTrainName(trainData.getTrainName());
        bks.setFilterDate(trainData.getFilterDate());
        bks.setFromDestination(trainData.getFromDestination());
        bks.setToDestination(trainData.getToDestination());
        bks.setTime(trainData.getTime());
        bks.setTotalCalculated(trainData.getTotalCalculated());
        if (trainData.isTripStatus()) {
            bks.setTripStatus("Active");
        } else {
            bks.setTripStatus("Canceled");
        }

        return bks;
    }
}
