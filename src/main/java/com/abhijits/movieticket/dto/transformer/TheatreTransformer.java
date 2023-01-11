package com.abhijits.movieticket.dto.transformer;

import com.abhijits.movieticket.domain.theatre.Theatre;
import com.abhijits.movieticket.dto.theatre.TheatreDto;
import org.springframework.stereotype.Component;

/**
 * Created by   : Abhijit Singh
 * On           : 09 January, 2023
 */
@Component
public class TheatreTransformer implements Transformer<Theatre, TheatreDto> {
    @Override
    public TheatreDto toDto(Theatre entity) {
        return new TheatreDto()
                .setUuid(entity.getUuid())
                .setName(entity.getName());
    }

    @Override
    public Theatre toEntity(TheatreDto theatreDto) {
        throw new UnsupportedOperationException("Method not implemented.");
    }
}
