package com.abhijits.movieticket.repository.theatre;

import com.abhijits.movieticket.domain.theatre.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Created by   : Abhijit Singh
 * On           : 08 January, 2023
 */
public interface MovieRepository extends JpaRepository<Movie, UUID> {
}
