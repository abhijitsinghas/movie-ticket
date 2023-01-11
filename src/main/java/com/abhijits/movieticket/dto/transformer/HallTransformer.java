package com.abhijits.movieticket.dto.transformer;

import com.abhijits.movieticket.domain.theatre.Hall;
import com.abhijits.movieticket.dto.theatre.HallDto;
import org.springframework.stereotype.Component;

/**
 * Created by   : Abhijit Singh
 * On           : 09 January, 2023
 */
@Component
public class HallTransformer implements Transformer<Hall, HallDto> {
    @Override
    public HallDto toDto(Hall entity) {
        return new HallDto()
                .setUuid(entity.getUuid())
                .setName(entity.getName());
    }

    @Override
    public Hall toEntity(HallDto hallDto) {
        throw new UnsupportedOperationException("Method not implemented.");
    }
}
