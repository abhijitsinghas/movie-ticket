package com.abhijits.movieticket.dto.booking;

import com.abhijits.movieticket.domain.enums.BookingStatus;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Created by   : Abhijit Singh
 * On           : 10 January, 2023
 */
@Data
@Accessors(chain = true)
public class BookingDto {

    private UUID uuid;

    private BookingStatus status;

    private ZonedDateTime bookedOn;

    private Integer noOfSeats;
}
