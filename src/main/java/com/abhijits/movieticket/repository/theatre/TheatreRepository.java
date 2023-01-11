package com.abhijits.movieticket.repository.theatre;

import com.abhijits.movieticket.domain.theatre.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * Created by   : Abhijit Singh
 * On           : 08 January, 2023
 */
public interface TheatreRepository extends JpaRepository<Theatre, UUID> {

    List<Theatre> findAllByAddress_CityUuid(UUID cityUuid);

}
