package com.abhijits.movieticket.dto.transformer;

import com.abhijits.movieticket.domain.booking.Booking;
import com.abhijits.movieticket.dto.booking.BookingDto;
import org.springframework.stereotype.Component;

/**
 * Created by   : Abhijit Singh
 * On           : 10 January, 2023
 */
@Component
public class BookingTransformer implements Transformer<Booking, BookingDto> {

    @Override
    public BookingDto toDto(Booking entity) {
        return new BookingDto()
                .setUuid(entity.getUuid())
                .setBookedOn(entity.getBookedOn())
                .setNoOfSeats(entity.getNumberOfSeats())
                .setStatus(entity.getStatus());
    }

    @Override
    public Booking toEntity(BookingDto bookingDto) {
        throw new UnsupportedOperationException("Method not implemented.");
    }
}
