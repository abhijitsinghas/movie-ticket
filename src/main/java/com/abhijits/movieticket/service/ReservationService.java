package com.abhijits.movieticket.service;

import com.abhijits.movieticket.domain.booking.Booking;
import com.abhijits.movieticket.domain.enums.ReservationStatus;
import com.abhijits.movieticket.domain.enums.SeatType;
import com.abhijits.movieticket.domain.theatre.Reservation;
import com.abhijits.movieticket.domain.theatre.Seat;
import com.abhijits.movieticket.domain.theatre.Show;
import com.abhijits.movieticket.repository.theatre.ReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by   : Abhijit Singh
 * On           : 10 January, 2023
 */
@Service
@Transactional
public class ReservationService {

    private ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Set<Reservation> findByShowAndSeat(Show show, Collection<Seat> seats) {
        return reservationRepository.findAllByShow_UuidAndSeat_UuidIn(show.getUuid(), seats.stream().map(Seat::getUuid).collect(Collectors.toSet()));
    }

    public Set<Reservation> book(Set<Reservation> reservations) {
        return reservations.stream()
                           .map(reservation -> reservationRepository.save(reservation.setStatus(ReservationStatus.BOOKED)))
                           .collect(Collectors.toSet());
    }

    public void delete(Collection<Reservation> reservations) {
        reservationRepository.deleteByUuids(reservations.stream().map(Reservation::getUuid).collect(Collectors.toSet()));
    }

    public boolean isReservable(Set<Reservation> reservations) {
        // if no reservation exist than return false
        if (reservations.isEmpty()) {
            return true;
        }

        ZonedDateTime currentTime = ZonedDateTime.now();

        // Reservations are valid and not reservable when
        // 1) Reservation Status is Booked or
        // 2) Reservation Status is Reserved and expiryTime is not reached.
        return ! reservations.stream()
                             .filter(reservation ->
                                     reservation.getStatus() == ReservationStatus.BOOKED
                                             || ( reservation.getStatus() == ReservationStatus.RESERVED && currentTime.isBefore(reservation.getExpiryTime()))
                             ).findAny().isPresent();
    }

    public Set<Reservation> reserve(Booking booking, Show show, Collection<Seat> seats) {
        ZonedDateTime expiryTime = ZonedDateTime.now().plusMinutes(5);

        return seats.stream()
                    .map(seat -> reservationRepository.save(
                            new Reservation()
                                    .setSeat(seat)
                                    .setShow(show)
                                    .setExpiryTime(expiryTime)
                                    .setStatus(ReservationStatus.RESERVED)
                                    .setPrice(getPrice(show, seat))
                                    .setBooking(booking)
                    ))
                    .collect(Collectors.toSet());
    }

    /**
     * Function to get price ideally to be fetched from table keeping price information for Show and SeatType combination.
     * @param show
     * @param seat
     * @return
     */
    public double getPrice(Show show, Seat seat) {
        return seat.getType() == SeatType.REGULAR? 100.0 : 200.0;
    }
}
