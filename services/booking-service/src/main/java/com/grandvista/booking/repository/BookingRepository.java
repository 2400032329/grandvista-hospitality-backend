package com.grandvista.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.grandvista.booking.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {

}