package com.abhijits.movieticket.dto.theatre;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Set;
import java.util.UUID;

/**
 * Created by   : Abhijit Singh
 * On           : 09 January, 2023
 */
@Data
@Accessors(chain = true)
public class TheatreDto {

    private UUID uuid;
    private String name;
    private Set<HallDto> halls;

}
