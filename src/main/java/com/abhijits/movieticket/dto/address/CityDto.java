package com.abhijits.movieticket.dto.address;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

/**
 * Created by   : Abhijit Singh
 * On           : 09 January, 2023
 */
@Data
@Accessors(chain = true)
public class CityDto {

    private UUID uuid;
    private String city;

}
