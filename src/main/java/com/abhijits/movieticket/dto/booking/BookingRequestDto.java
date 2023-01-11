package com.abhijits.movieticket.dto.booking;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.UUID;

/**
 * Created by   : Abhijit Singh
 * On           : 10 January, 2023
 */
@Data
@Accessors(chain = true)
public class BookingRequestDto {
    private UUID userUuid;

    private UUID showUuid;

    private List<UUID> seatUuids;
}
