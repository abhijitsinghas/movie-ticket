package com.abhijits.movieticket.repository.booking;

import com.abhijits.movieticket.domain.booking.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Created by   : Abhijit Singh
 * On           : 08 January, 2023
 */
public interface BookingRepository extends JpaRepository<Booking, UUID> {
}
