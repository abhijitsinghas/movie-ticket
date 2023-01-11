package com.abhijits.movieticket.service;

import com.abhijits.movieticket.domain.booking.Booking;
import com.abhijits.movieticket.domain.enums.BookingStatus;
import com.abhijits.movieticket.domain.enums.SeatType;
import com.abhijits.movieticket.domain.theatre.Reservation;
import com.abhijits.movieticket.domain.theatre.Seat;
import com.abhijits.movieticket.domain.theatre.Show;
import com.abhijits.movieticket.domain.users.PlatformUser;
import com.abhijits.movieticket.dto.booking.BookingRequestDto;
import com.abhijits.movieticket.repository.booking.BookingRepository;
import com.abhijits.movieticket.repository.theatre.SeatRepository;
import com.abhijits.movieticket.repository.theatre.ShowRepository;
import com.abhijits.movieticket.repository.user.PlatformUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.*;

/**
 * Created by   : Abhijit Singh
 * On           : 09 January, 2023
 */
@Service
public class BookingService {

    private ReservationService reservationService;
    private BookingRepository bookingRepository;
    private ShowRepository showRepository;
    private SeatRepository seatRepository;
    private PlatformUserRepository platformUserRepository;
    private PaymentService paymentService;

    public BookingService(
            ReservationService reservationService,
            BookingRepository bookingRepository,
            ShowRepository showRepository,
            SeatRepository seatRepository,
            PlatformUserRepository platformUserRepository,
            PaymentService paymentService
    ) {
        this.reservationService = reservationService;
        this.bookingRepository = bookingRepository;
        this.showRepository = showRepository;
        this.seatRepository = seatRepository;
        this.platformUserRepository = platformUserRepository;
        this.paymentService = paymentService;
    }

    public Booking get(UUID bookingUuid) {
        return bookingRepository.findById(bookingUuid).orElseThrow(() -> new IllegalStateException("Booking Not Found"));
    }

    @Transactional
    public Booking book(BookingRequestDto bookingRequestDto) {
        PlatformUser user = platformUserRepository.findById(bookingRequestDto.getUserUuid()).orElseThrow(() -> new IllegalArgumentException("User Not Found."));
        Show show = showRepository.findById(bookingRequestDto.getShowUuid()).orElseThrow(() -> new IllegalArgumentException("Show Not Found."));

        List<Seat> seats = seatRepository.findAllById(bookingRequestDto.getSeatUuids());

        if (seats.size() != bookingRequestDto.getSeatUuids().size()) {
            throw new IllegalArgumentException("Seat Not Found.");
        }

        return createBooking(user, show, seats);
    }

    @Transactional
    public Booking paymentSuccessful(UUID bookingUuid) {

        Optional<Booking> bookingOptional = bookingRepository.findById(bookingUuid);

        if (bookingOptional.isEmpty()) {
            // Trigger Payment reversal/refund event.
            throw new IllegalStateException("Booking not found.");
        }

        Booking booking = bookingOptional.get();

        if (booking.getNumberOfSeats() != booking.getReservation().size()) {
            // Trigger Payment reversal/refund event.
            throw new IllegalStateException("Seats were already booked by another user.");
        }

        reservationService.book(booking.getReservation());

        booking.setStatus(BookingStatus.CONFIRMED);

        return bookingRepository.save(booking);
    }

    private Booking createBooking(PlatformUser user, Show show, Collection<Seat> seats) {
        Set<Reservation> existingReservations = reservationService.findByShowAndSeat(show, seats);

        if (!existingReservations.isEmpty()) {
            if (reservationService.isReservable(existingReservations)) {
                reservationService.delete(existingReservations);
            } else {
                throw new IllegalStateException("Seats are not Reservable.");
            }
        }

        Booking booking =  bookingRepository.save(new Booking()
                .setUser(user)
                .setStatus(BookingStatus.PENDING)
                .setNumberOfSeats(seats.size())
                .setBookedOn(ZonedDateTime.now()));

        Set<Reservation> currentReservation = reservationService.reserve(booking, show, seats);

        booking.setReservation(currentReservation);
        booking.setPayment(paymentService.createPayment(currentReservation.stream().mapToDouble(Reservation::getPrice).sum()));

        return bookingRepository.save(booking);
    }

}
