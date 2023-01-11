package com.abhijits.movieticket.dto.transformer;

import com.abhijits.movieticket.domain.theatre.Show;
import com.abhijits.movieticket.dto.theatre.ShowDto;
import org.springframework.stereotype.Component;

/**
 * Created by   : Abhijit Singh
 * On           : 09 January, 2023
 */
@Component
public class ShowTransformer implements Transformer<Show, ShowDto> {

    @Override
    public ShowDto toDto(Show entity) {
        return new ShowDto()
                .setUuid(entity.getUuid())
                .setStartTime(entity.getStartTime())
                .setEndTime(entity.getEndTime());
    }

    @Override
    public Show toEntity(ShowDto showDto) {
        throw new UnsupportedOperationException("Method not implemented.");
    }
}
