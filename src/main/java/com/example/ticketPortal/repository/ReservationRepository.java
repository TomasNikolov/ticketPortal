package com.example.ticketPortal.repository;

import com.example.ticketPortal.entity.ReservationData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationData, Integer> {
    @Query(value = "select * from reservation  where reservation.to_destination =:to and reservation.from_destination =:from and reservation.filter_date =:date  order By reservation.filter_date desc ", nativeQuery = true)
    List<ReservationData> findByToFromAndDate(String to, String from, String date);
}
