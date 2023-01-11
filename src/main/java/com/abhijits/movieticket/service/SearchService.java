package com.abhijits.movieticket.service;

import com.abhijits.movieticket.domain.address.City;
import com.abhijits.movieticket.domain.theatre.Hall;
import com.abhijits.movieticket.domain.theatre.Movie;
import com.abhijits.movieticket.domain.theatre.Show;
import com.abhijits.movieticket.domain.theatre.Theatre;
import com.abhijits.movieticket.dto.address.CityDto;
import com.abhijits.movieticket.dto.theatre.HallDto;
import com.abhijits.movieticket.dto.theatre.MovieDto;
import com.abhijits.movieticket.dto.theatre.ShowDto;
import com.abhijits.movieticket.dto.theatre.TheatreDto;
import com.abhijits.movieticket.dto.transformer.HallTransformer;
import com.abhijits.movieticket.dto.transformer.ShowTransformer;
import com.abhijits.movieticket.dto.transformer.TheatreTransformer;
import com.abhijits.movieticket.repository.address.CityRepository;
import com.abhijits.movieticket.repository.theatre.MovieRepository;
import com.abhijits.movieticket.repository.theatre.TheatreRepository;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by   : Abhijit Singh
 * On           : 08 January, 2023
 */
@Service
public class SearchService {

    private CityRepository cityRepository;
    private MovieRepository movieRepository;
    private TheatreRepository theatreRepository;
    private ShowTransformer showTransformer;
    private HallTransformer hallTransformer;
    private TheatreTransformer theatreTransformer;

    public SearchService(
            CityRepository cityRepository,
            MovieRepository movieRepository,
            TheatreRepository theatreRepository,
            ShowTransformer showTransformer,
            HallTransformer hallTransformer,
            TheatreTransformer theatreTransformer
    ) {
        this.cityRepository = cityRepository;
        this.movieRepository = movieRepository;
        this.theatreRepository = theatreRepository;
        this.showTransformer = showTransformer;
        this.hallTransformer = hallTransformer;
        this.theatreTransformer = theatreTransformer;
    }

    public Set<TheatreDto> search(UUID cityUuid, UUID movieUuid, ZonedDateTime date) {

        if (date.truncatedTo(ChronoUnit.DAYS).isBefore(ZonedDateTime.now().truncatedTo(ChronoUnit.DAYS))) {
            throw new IllegalArgumentException("Date cannot be in Past.");
        }

        City city = cityRepository.findById(cityUuid).orElseThrow(()-> new IllegalArgumentException("City Not Found."));

        Movie movie = movieRepository.findById(movieUuid).orElseThrow(() -> new IllegalArgumentException("Movie Not Found."));

        List<Theatre> theatres = theatreRepository.findAllByAddress_CityUuid(city.getUuid());

        return filterTheatre(theatres, movie.getUuid(), date);
    }

    private Set<TheatreDto> filterTheatre(Collection<Theatre> theatres, UUID movieUuid, ZonedDateTime date) {
        return theatres.stream()
                         .map(theatre -> theatreTransformer.toDto(theatre).setHalls(filterHalls(theatre.getHalls(), movieUuid, date)))
                         .filter(theatreDto -> !theatreDto.getHalls().isEmpty())
                         .collect(Collectors.toSet());
    }

    private Set<HallDto> filterHalls(Collection<Hall> halls, UUID movieUuid, ZonedDateTime date) {
        return halls.stream()
                .map(hall -> hallTransformer.toDto(hall).setShows(filterShows(hall.getShows(), movieUuid, date)))
                .filter(hallDto -> !hallDto.getShows().isEmpty())
                .collect(Collectors.toSet());
    }

    private Set<ShowDto> filterShows(Collection<Show> shows, UUID movieUuid, ZonedDateTime date) {
        return shows.stream()
                    .filter(show -> show.getMovie().getUuid().equals(movieUuid) && show.getStartTime().toLocalDate().atStartOfDay().equals(date.toLocalDate().atStartOfDay()) )
                    .map(showTransformer::toDto)
                    .collect(Collectors.toSet());
    }

}
