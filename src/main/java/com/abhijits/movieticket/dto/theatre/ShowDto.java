package com.abhijits.movieticket.dto.theatre;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * Created by   : Abhijit Singh
 * On           : 09 January, 2023
 */
@Data
@Accessors(chain = true)
public class ShowDto {

    private UUID uuid;
    private ZonedDateTime startTime;
    private ZonedDateTime endTime;

}
